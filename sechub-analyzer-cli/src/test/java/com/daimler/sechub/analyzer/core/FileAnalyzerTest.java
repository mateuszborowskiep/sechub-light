package com.daimler.sechub.analyzer.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.daimler.analyzer.model.Marker;
import com.daimler.analyzer.model.MarkerPair;
import com.daimler.analyzer.model.MarkerType;

public class FileAnalyzerTest {

    final String path = "src/test/resources/";

    @Test
    public void test_processFile__pair() throws IOException {
        /* prepare */
        Marker start = new Marker(MarkerType.START, 3, 3);
        Marker end = new Marker(MarkerType.END, 9, 3);
        MarkerPair pair = new MarkerPair();
        pair.setEnd(end);
        pair.setStart(start);
        List<MarkerPair> expectedPairs = new LinkedList<>();
        expectedPairs.add(pair);
        
        File file = new File(path + "test_pair.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__multiple() throws IOException {
        /* prepare */
        List<MarkerPair> expectedPairs = new LinkedList<>();
        
        Marker start = new Marker(MarkerType.START, 4, 4);
        Marker end = new Marker(MarkerType.END, 7, 5);
        MarkerPair pair = new MarkerPair();
        pair.setEnd(end);
        pair.setStart(start);
        
        Marker start2 = new Marker(MarkerType.START, 10, 9);
        Marker end2 = new Marker(MarkerType.END, 12, 3);
        MarkerPair pair2 = new MarkerPair();
        pair2.setEnd(end2);
        pair2.setStart(start2);
        
        Marker start3 = new Marker(MarkerType.START, 15, 3);
        Marker end3 = new Marker(MarkerType.END, 18, 3);
        MarkerPair pair3 = new MarkerPair();
        pair3.setEnd(end3);
        pair3.setStart(start3);
        
        expectedPairs.add(pair);
        expectedPairs.add(pair2);
        expectedPairs.add(pair3);
        
        File file = new File(path + "test_multiple.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__start_only() throws IOException {
        /* prepare */
        List<MarkerPair> expectedPairs = new LinkedList<>();
        
        File file = new File(path + "test_only_start.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__end_only() throws IOException {
        /* prepare */
        List<MarkerPair> expectedPairs = new LinkedList<>();
        
        File file = new File(path + "test_only_end.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__two_ends() throws IOException {
        /* prepare */
        Marker start = new Marker(MarkerType.START, 3, 3);
        Marker end = new Marker(MarkerType.END, 9, 3);
        MarkerPair pair = new MarkerPair();
        pair.setEnd(end);
        pair.setStart(start);
        List<MarkerPair> expectedPairs = new LinkedList<>();
        expectedPairs.add(pair);
        
        File file = new File(path + "test_two_ends.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__two_starts() throws IOException {
        /* prepare */
        Marker start = new Marker(MarkerType.START, 3, 3);
        Marker end = new Marker(MarkerType.END, 15, 2);
        MarkerPair pair = new MarkerPair();
        pair.setEnd(end);
        pair.setStart(start);
        List<MarkerPair> expectedPairs = new LinkedList<>();
        expectedPairs.add(pair);
        
        File file = new File(path + "test_two_starts.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__c_single_comment() throws IOException {
        /* prepare */
        String codePath = path + "code/";
        
        List<MarkerPair> expectedPairs = createMarkerPairsOf(4, 5, 6, 5);
        
        File file = new File(codePath + "single_line.c");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__c_multiline_comment() throws IOException {
        /* prepare */
        String codePath = path + "code/";
        
        List<MarkerPair> expectedPairs = createMarkerPairsOf(4, 5, 8, 5);
        
        File file = new File(codePath + "multi_line.c");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__c_multiline_comment_comment_not_beginning() throws IOException {
        /* prepare */
        String codePath = path + "code/";
        
        List<MarkerPair> expectedPairs = new LinkedList<>();
        
        File file = new File(codePath + "multi_line_comment_not_beginning.c");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__java_multiline_comment() throws IOException {
        /* prepare */
        String codePath = path + "code/";
        
        List<MarkerPair> expectedPairs = createMarkerPairsOf(5, 11, 7, 11);
        
        File file = new File(codePath + "MultiLineComment.java");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__java_multiline_comment_not_beginning() throws IOException {
        /* prepare */
        String codePath = path + "code/";
        
        List<MarkerPair> expectedPairs = new LinkedList<>();
        
        File file = new File(codePath + "MultiLineCommentNotBeginning.java");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__java_single_line() throws IOException {
        /* prepare */
        String codePath = path + "code/";
        
        List<MarkerPair> expectedPairs = createMarkerPairsOf(6, 9, 8, 9);
        
        File file = new File(codePath + "SingleLineComment.java");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs, is(expectedPairs));
    }
    
    @Test
    public void test_processFile__no_markers() throws IOException {
        /* prepare */
        File file = new File(path + "test_no_markers.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs.isEmpty(), is(true));
    }
    
    @Test
    public void test_processFile__same_line() throws IOException {
        /* prepare */
        File file = new File(path + "test_same_line.txt");
        
        /* execute */
        List<MarkerPair> actualPairs = FileAnalyzer.getInstance().processFile(file);
        
        /* test */
        assertThat(actualPairs.isEmpty(), is(true));
    }
    
    @Test
    public void test_processFile__file_not_found() {
        /* prepare */
        File file = new File(path + "not_found.txt");
        String exceptionMessage = file.getPath() + " (No such file or directory)";
        
        try {
            /* execute */
            FileAnalyzer.getInstance().processFile(file);
            fail("The file does not exist. An exception was expected.");
        } catch (FileNotFoundException e) {
            /* test */
            assertThat(e.getMessage(), is(exceptionMessage));
        } catch (IOException e) {
            fail("Unexpected exception.");
        }
    }
    
    private List<MarkerPair> createMarkerPairsOf(int startLine, int startColumn, int endLine, int endColumn) {
        Marker start = new Marker(MarkerType.START, startLine, startColumn);
        Marker end = new Marker(MarkerType.END, endLine, endColumn);
        MarkerPair pair = new MarkerPair();
        pair.setEnd(end);
        pair.setStart(start);
        List<MarkerPair> pairs = new LinkedList<>();
        pairs.add(pair);
        
        return pairs;
    }
}
