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
package fr.opensagres.eclipse.jsbuild.internal.ui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Utility class to handle image resources.
 */
public class ImageResource {
	// the image registry
	private static ImageRegistry imageRegistry;

	// map of image descriptors since these
	// will be lost by the image registry
	private static Map<String, ImageDescriptor> imageDescriptors;

	// base urls for images
	private static URL ICON_BASE_URL;

	private static final String URL_DLCL = "full/dlcl16/";
	private static final String URL_ELCL = "full/elcl16/";
	private static final String URL_OBJ = "full/obj16/";

	// General Object Images
	public static final String IMG_ADD = "add";
	public static final String IMG_REFRESH = "refresh";
	public static final String IMG_REMOVE_ALL = "removeall_co";
	public static final String IMG_REMOVE = "remove_co";
	public static final String IMG_RUN = "run";
	public static final String IMG_GOTO_DEF = "goto_def";

	static {
		try {
			String pathSuffix = "icons/";
			ICON_BASE_URL = JSBuildFileUIPlugin.getDefault().getBundle()
					.getEntry(pathSuffix);
		} catch (Exception e) {
			Logger.logException("Images error", e);
		}
	}

	/**
	 * Cannot construct an ImageResource. Use static methods only.
	 */
	private ImageResource() {
		// do nothing
	}

	/**
	 * Dispose of element images that were created.
	 */
	protected static void dispose() {
		// do nothing
	}

	/**
	 * Return the image with the given key.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.swt.graphics.Image
	 */
	public static Image getImage(String key) {
		return getImage(key, null);
	}

	/**
	 * Return the image with the given key.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.swt.graphics.Image
	 */
	public static Image getImage(String key, String keyIfImageNull) {
		if (imageRegistry == null)
			initializeImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null) {
			if (keyIfImageNull != null) {
				return getImage(keyIfImageNull, null);
			}
			imageRegistry.put(key, ImageDescriptor.getMissingImageDescriptor());
			image = imageRegistry.get(key);
		}
		return image;
	}

	/**
	 * Return the image descriptor with the given key.
	 * 
	 * @param key
	 *            java.lang.String
	 * @return org.eclipse.jface.resource.ImageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		if (imageRegistry == null)
			initializeImageRegistry();
		ImageDescriptor id = imageDescriptors.get(key);
		if (id != null)
			return id;

		return ImageDescriptor.getMissingImageDescriptor();
	}

	/**
	 * Initialize the image resources.
	 */
	protected static void initializeImageRegistry() {
		imageRegistry = JSBuildFileUIPlugin.getDefault().getImageRegistry();
		imageDescriptors = new HashMap<String, ImageDescriptor>();

		// load general object images
		registerImage(IMG_ADD, URL_ELCL + IMG_ADD + ".png");
		registerImage(IMG_REFRESH, URL_ELCL + IMG_REFRESH + ".gif");
		registerImage(IMG_REMOVE_ALL, URL_ELCL + IMG_REMOVE_ALL + ".png");
		registerImage(IMG_REMOVE, URL_ELCL + IMG_REMOVE + ".png");
		registerImage(IMG_RUN, URL_ELCL + IMG_RUN + ".png");
		registerImage(IMG_GOTO_DEF, URL_ELCL + IMG_GOTO_DEF + ".png");

	}

	/**
	 * Register an image with the registry.
	 * 
	 * @param key
	 *            java.lang.String
	 * @param partialURL
	 *            java.lang.String
	 */
	public static void registerImage(String key, String partialURL) {
		try {
			ImageDescriptor id = ImageDescriptor.createFromURL(new URL(
					ICON_BASE_URL, partialURL));
			registerImageDescriptor(key, id);
		} catch (Exception e) {
			Logger.logException("Error registering image " + key + " from "
					+ partialURL, e);
		}
	}

	public static void registerImageDescriptor(String key, ImageDescriptor id) {
		if (imageRegistry == null)
			initializeImageRegistry();
		imageRegistry.put(key, id);
		imageDescriptors.put(key, id);
	}

}