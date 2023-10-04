-- SPDX-License-Identifier: MIT

-- indices on statistics tables to enhance reporting performance

CREATE INDEX IF NOT EXISTS i01_statistic_job_run_project ON statistic_job_run (project_id);
CREATE INDEX IF NOT EXISTS i02_statistic_job_run_month ON statistic_job_run (DATE_TRUNC('month', created));

CREATE INDEX IF NOT EXISTS i01_statistic_job_run_data_filter ON statistic_job_run_data (type,id);
