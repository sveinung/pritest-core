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

import japa.parser.ast.CompilationUnit;
import no.pritest.prioritization.methodcoverage.model.ClassCover;
import no.pritest.prioritization.methodcoverage.model.ClassType;
import no.pritest.prioritization.methodcoverage.visitor.MethodCoverageVisitor;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodCoverageProvider {

    private static Logger logger = Logger.getLogger(MethodCoverageProvider.class);

	private final Map<String, ClassType> classTypes;
	private Map<String, ClassCover> classCovers;
	private final List<CompilationUnit> compilationUnits;
	
	public MethodCoverageProvider(Map<String, ClassType> classTypes, List<CompilationUnit> compilationUnits) {
		this.classTypes = classTypes;
		this.compilationUnits = compilationUnits;

        try {
            logger.addAppender(new FileAppender(new SimpleLayout(), "logs/MethodCoverageProvider.log"));
        } catch (IOException e1) {
        }

		retrieveMethodCoverage();
	}

	private void retrieveMethodCoverage() {
		MethodCoverageVisitor mcv;
		classCovers = new HashMap<String, ClassCover>();
		for(CompilationUnit cu : compilationUnits){
            try {
                mcv = new MethodCoverageVisitor(classTypes);
                cu.accept(mcv, null);
                classCovers.putAll(mcv.getCoveredClasses());
            } catch (Exception e) {
//                logger.warn("Unsupported java syntax");
                logger.warn("Unsupported java syntax", e);
//                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
//                    logger.warn(stackTraceElement.toString());
//                }
            }
		}
	}

	public Map<String, ClassCover> getMethodCoverage() {
		return classCovers;
	}

}
