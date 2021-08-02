-- https://github.com/payara/Payara/tree/master/appserver/batch/batch-database/src/main/resources/glassfish/lib/install/databases

CREATE TABLE APP.JOBINSTANCEDATA(
  jobinstanceid BIGINT IDENTITY NOT NULL PRIMARY KEY,
  name		    VARCHAR(512), 
  apptag        VARCHAR(512)
);

CREATE TABLE APP.EXECUTIONINSTANCEDATA(
  jobexecid         BIGINT IDENTITY NOT NULL PRIMARY KEY,
  jobinstanceid     BIGINT,
  createtime	    TIMESTAMP,
  starttime		    TIMESTAMP,
  endtime		    TIMESTAMP,
  updatetime	    TIMESTAMP,
  parameters	    BLOB,
  batchstatus		VARCHAR(512),
  exitstatus		VARCHAR(512),
  CONSTRAINT JOBINST_JOBEXEC_FK FOREIGN KEY (jobinstanceid) REFERENCES JOBINSTANCEDATA (jobinstanceid)
);
  
CREATE TABLE APP.STEPEXECUTIONINSTANCEDATA(
	stepexecid          BIGINT IDENTITY NOT NULL PRIMARY KEY,
	jobexecid	        BIGINT,
	batchstatus         VARCHAR(512),
    exitstatus			VARCHAR(512),
    stepname			VARCHAR(512),
	readcount			INTEGER,
	writecount			INTEGER,
	commitcount         INTEGER,
	rollbackcount		INTEGER,
	readskipcount		INTEGER,
	processskipcount	INTEGER,
	filtercount			INTEGER,
	writeskipcount		INTEGER,
	startTime           TIMESTAMP,
	endTime             TIMESTAMP,
	persistentData		BLOB,
	CONSTRAINT JOBEXEC_STEPEXEC_FK FOREIGN KEY (jobexecid) REFERENCES EXECUTIONINSTANCEDATA (jobexecid)
); 

CREATE TABLE APP.JOBSTATUS (
  id        BIGINT IDENTITY NOT NULL PRIMARY KEY,
  obj		BLOB,
  CONSTRAINT JOBSTATUS_JOBINST_FK FOREIGN KEY (id) REFERENCES JOBINSTANCEDATA (jobinstanceid) ON DELETE CASCADE
);

CREATE TABLE APP.STEPSTATUS(
  id        BIGINT IDENTITY NOT NULL PRIMARY KEY,
  obj		BLOB,
  CONSTRAINT STEPSTATUS_STEPEXEC_FK FOREIGN KEY (id) REFERENCES STEPEXECUTIONINSTANCEDATA (stepexecid) ON DELETE CASCADE
);

CREATE TABLE APP.CHECKPOINTDATA(
  id		VARCHAR(512),
  obj		BLOB
);