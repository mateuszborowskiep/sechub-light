// SPDX-License-Identifier: MIT
package com.daimler.sechub.sereco.importer;

import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.daimler.sechub.sereco.metadata.Classification;
import com.daimler.sechub.sereco.metadata.MetaData;
import com.daimler.sechub.sereco.metadata.Vulnerability;

@Component
public class NetsparkerV1XMLImporter extends AbstractProductResultImporter {

	public MetaData importResult(String xml) throws IOException{
		MetaData metaData = new MetaData();
		if (xml==null) {
			xml="";
		}
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			throw new IOException("Import cannot parse xml",e);
		}

		Element netsparkerCloudElement = document.getRootElement();
		Element vulnerabilitiesElement = netsparkerCloudElement.element("vulnerabilities");
		if (vulnerabilitiesElement==null) {
			throw new IllegalStateException("no vulnerabilities element found!");
		}
		@SuppressWarnings("unchecked")
		Iterator<Element> it = vulnerabilitiesElement.elementIterator();
		while (it.hasNext()) {
			Element vulnerabilityElement = it.next();
			Vulnerability vulnerability = new Vulnerability();
			metaData.getVulnerabilities().add(vulnerability);

			vulnerability.setSeverity(NetsparkerServerityConverter.convert(vulnerabilityElement.elementText("severity")));
			vulnerability.setUrl(vulnerabilityElement.elementText("url"));
			vulnerability.setType(vulnerabilityElement.elementText("type"));
			vulnerability.setDescription(vulnerabilityElement.elementText("description"));

			Element classificationElement = vulnerabilityElement.element("classification");
			if (classificationElement==null) {
				throw new IllegalStateException("no classificaton element found!");
			}
			Classification classification = vulnerability.getClassification();
			classification.setOwasp(classificationElement.elementText("owasp"));
			classification.setWasc(classificationElement.elementText("wasc"));
			classification.setCwe(classificationElement.elementText("cwe"));
			classification.setCapec(classificationElement.elementText("capec"));
			classification.setPci31(classificationElement.elementText("pci31"));
			classification.setPci32(classificationElement.elementText("pci32"));
			classification.setHipaa(classificationElement.elementText("hipaa"));
			classification.setOwaspProactiveControls(classificationElement.elementText("owasppc"));
		}
		return metaData;
	}

	@Override
	protected ImportSupport createImportSupport() {
		/* @formatter:off */
			return ImportSupport.
								builder().
									productId("Netsparker").
									mustBeXML().
									contentIdentifiedBy("<netsparker-cloud").
									build();
			/* @formatter:on */
	}




}