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
import no.pritest.prioritization.methodcoverage.model.ClassType;
import no.pritest.prioritization.methodcoverage.visitor.CompilationUnitVisitor;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTypeProvider {

    private static Logger logger = Logger.getLogger(ClassTypeProvider.class);

	private List<CompilationUnit> compilationUnits;
	private Map<String, ClassType> classTypeMap = new HashMap<String, ClassType>();
	
	public ClassTypeProvider(List<CompilationUnit> compilationUnits){
		this.compilationUnits = compilationUnits;

        try {
            logger.addAppender(new FileAppender(new SimpleLayout(), "logs/ClassTypeProvider.log"));
        } catch (IOException e1) {
        }

		retrieveClassTypes();
	}
	
	private void retrieveClassTypes() {
		CompilationUnitVisitor cuv;
		for(CompilationUnit cu : compilationUnits){
            try {
                cuv = new CompilationUnitVisitor();
                cu.accept(cuv, null);
                classTypeMap.putAll(cuv.getTypesAsMapItems());
                
            } catch (Exception e) {
            	logger.warn("Unsupported java syntax", e);
            }
		}
	}

	public Map<String, ClassType> getClassTypes() {
		return this.classTypeMap;
	}

}
