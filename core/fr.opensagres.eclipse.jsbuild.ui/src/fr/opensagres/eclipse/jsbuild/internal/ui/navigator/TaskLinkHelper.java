/**
 *  Copyright (c) 2013-2014 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package fr.opensagres.eclipse.jsbuild.internal.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import fr.opensagres.eclipse.jsbuild.core.IJSBuildFileNode;
import fr.opensagres.eclipse.jsbuild.internal.ui.EditorUtility;

public class TaskLinkHelper implements ILinkHelper {

	@Override
	public void activateEditor(IWorkbenchPage page,
			IStructuredSelection selection) {
		if (selection == null || selection.isEmpty())
			return;
		Object element = selection.getFirstElement();
		if (element instanceof IJSBuildFileNode) {
			IJSBuildFileNode node = (IJSBuildFileNode) element;
			IEditorPart part = EditorUtility.isOpenInEditor(node);
			if (part != null) {
				page.bringToTop(part);
				if (element instanceof IJSBuildFileNode)
					EditorUtility.revealInEditor(part, node);
			}
		}
	}

	@Override
	public IStructuredSelection findSelection(IEditorInput input) {
		/*
		 * IJavaScriptElement element = JavaScriptUI
		 * .getEditorInputJavaElement(input); if (element == null) { IFile file
		 * = ResourceUtil.getFile(input); if (file != null) { element =
		 * JavaScriptCore.create(file); } } return (element != null) ? new
		 * StructuredSelection(element) : StructuredSelection.EMPTY;
		 */
		return StructuredSelection.EMPTY;
	}

}
