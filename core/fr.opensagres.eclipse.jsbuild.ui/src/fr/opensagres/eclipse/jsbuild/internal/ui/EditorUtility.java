package fr.opensagres.eclipse.jsbuild.internal.ui;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import fr.opensagres.eclipse.jsbuild.core.IJSBuildFileNode;
import fr.opensagres.eclipse.jsbuild.core.Location;

public class EditorUtility {

	public static void openInEditor(IWorkbenchPage page,
			IEditorDescriptor editorDescriptor, IJSBuildFileNode node) {
		IEditorPart editorPart = null;
		IFile fileResource = node.getBuildFile().getBuildFileResource();
		try {
			if (editorDescriptor == null) {
				editorPart = page.openEditor(new FileEditorInput(fileResource),
						IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
			} else {
				editorPart = page.openEditor(new FileEditorInput(fileResource),
						editorDescriptor.getId());
			}
		} catch (PartInitException e) {
			Logger.logException(MessageFormat.format(
					JSBuildFileUIMessages.JSBuildFileUtil_0,
					new Object[] { fileResource.getLocation().toOSString() }),
					e);
		}

		Location location = node.getLocation();
		if (location != null && location.getStart() > 0) {
			int start = location.getStart();
			int length = location.getLength();
			ITextEditor textEditor = null;
			if (editorPart instanceof ITextEditor)
				textEditor = (ITextEditor) editorPart;
			else if (editorPart instanceof IAdaptable)
				textEditor = (ITextEditor) editorPart
						.getAdapter(ITextEditor.class);
			if (textEditor != null) {
				//IDocument document = textEditor.getDocumentProvider()
				//		.getDocument(editorPart.getEditorInput());
				// int start = document.getLineOffset(line - 1);
				textEditor.selectAndReveal(start, length);
				page.activate(editorPart);
			} else {
				try {
					IMarker marker = fileResource
							.createMarker("org.eclipse.core.resources.textmarker");
					marker.setAttribute("lineNumber", start);
					editorPart = IDE.openEditor(page, marker, true);
					marker.delete();
				} catch (CoreException e) {
					Logger.logException(MessageFormat.format(
							JSBuildFileUIMessages.JSBuildFileUtil_0,
							new Object[] { fileResource.getLocation()
									.toOSString() }), e);
				}
			}
		}
	}
}