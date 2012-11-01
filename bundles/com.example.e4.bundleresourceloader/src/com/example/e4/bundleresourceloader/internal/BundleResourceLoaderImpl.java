package com.example.e4.bundleresourceloader.internal;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.example.e4.bundleresourceloader.IBundleResourceLoader;

public class BundleResourceLoaderImpl implements IBundleResourceLoader {

	ImageRegistry registry = new ImageRegistry(Display.getDefault());

	@Override
	public Image loadImage(Class<?> clazz, String path) {

		if (registry.get(path) == null) {
			Bundle bundle = FrameworkUtil.getBundle(clazz);
			URL url = FileLocator.find(bundle, new Path(path), null);
			ImageDescriptor imageDescr = ImageDescriptor.createFromURL(url);
			registry.put(path, imageDescr);
		}
		return registry.get(path);
	}
}
