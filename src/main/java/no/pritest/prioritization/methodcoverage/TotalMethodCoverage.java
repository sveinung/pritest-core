/**
    This file is part of Pritest.

    Pritest is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Pritest is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

package no.pritest.prioritization.methodcoverage;

import no.pritest.prioritization.methodcoverage.algorithm.MethodCoverageAlgorithm;
import no.pritest.prioritization.methodcoverage.model.SummarizedTestCase;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import java.io.IOException;
import japa.parser.ParseException;
import java.util.List;

public class TotalMethodCoverage extends MethodCoverage {

    private static Logger logger = Logger.getLogger(TotalMethodCoverage.class);

    public TotalMethodCoverage(String pathToProjectSource, String pathToTestSuite)
            throws IOException, ParseException {
        super();

        try {
            logger.addAppender(new FileAppender(new SimpleLayout(), "logs/TotalMethodCoverage.log"));
        } catch (IOException e1) {
        }

        prioritizeTestCases(pathToProjectSource, pathToTestSuite);
	}

    @Override
    protected void prioritizeTestCases(String pathToProjectSource, String pathToTestSuite)
            throws IOException, ParseException {
    	super.prioritizeTestCases(pathToProjectSource, pathToTestSuite);
    	
        List<SummarizedTestCase> prioritizedTestCases = MethodCoverageAlgorithm.totalMethodCoverage(getTestSuiteMethodCoverage(), getSourceMethodCoverage());

        logger.info("Method calls found:");
        for (SummarizedTestCase stc : prioritizedTestCases) {
            addTestCase(stc);
            logTestCase(stc);
        }
    }

    private void logTestCase(SummarizedTestCase stc) {
        logger.info(stc.getTestCase().getPackageName() + "." + stc.getTestCase().getName() + " " + stc.getSummarizedCoverage().size());
    }
}
