package com.knitml.renderer.visitor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.knitml.core.model.operations.block.GraftTogether;
import com.knitml.renderer.Renderer;
import com.knitml.renderer.common.RenderingException;
import com.knitml.renderer.event.impl.AbstractEventHandler;

public class GraftTogetherHandler extends AbstractEventHandler {

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory
			.getLogger(GraftTogetherHandler.class);

	public boolean begin(Object element, Renderer renderer)
			throws RenderingException {
		GraftTogether operation = (GraftTogether)element;
		renderer.renderGraftStitchesTogether(operation.getNeedles());
		return true;
	}

}
