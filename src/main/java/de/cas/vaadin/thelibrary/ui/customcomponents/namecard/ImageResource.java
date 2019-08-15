package de.cas.vaadin.thelibrary.ui.customcomponents.namecard;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

import java.io.File;

class ImageResource extends FileResource {

    private static final String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
            +"/WEB-INF/Images/";

    /**
     * Creates a new file resource for providing given file for client
     * terminals.
     *
     * @param name of the file that should be served.
     */

    ImageResource(String filename){
        super(new File(basepath+filename));
    }

}
