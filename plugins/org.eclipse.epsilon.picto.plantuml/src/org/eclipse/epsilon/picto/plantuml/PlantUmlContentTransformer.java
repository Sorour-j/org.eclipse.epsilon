/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.plantuml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;
import org.eclipse.epsilon.picto.transformers.ViewContentTransformer;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUmlContentTransformer implements ViewContentTransformer {

	@Override
	public boolean canTransform(ViewContent content) {
		return StringUtil.isOneOf(content.getFormat().toLowerCase(), "plantuml", "puml");
	}

	@Override
	public String getLabel(ViewContent content) {
		return "Plant UML";
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		return new ViewContent("svg", plantumlToSvg(content.getText()), content);
	}
	
	/**
	 * Converts PlantUML diagram to static SVG file.
	 * 
	 * @param plant The Plant UML description.
	 * @return The generated SVG as an XML string.
	 * @throws IOException If writing to file fails.
	 */
	public static String plantumlToSvg(String plant) throws IOException {
		SourceStringReader reader = new SourceStringReader(plant);
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
			return new String(os.toByteArray(), Charset.forName("UTF-8"));
		}
	}
}
