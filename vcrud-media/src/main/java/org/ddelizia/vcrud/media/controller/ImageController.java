package org.ddelizia.vcrud.media.controller;

import org.apache.commons.io.IOUtils;
import org.ddelizia.vcrud.core.controller.AbstractController;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.media.model.Image;
import org.ddelizia.vcrud.media.model.Image_;
import org.ddelizia.vcrud.media.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/08/13
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ImageController extends AbstractController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelService modelService;

    @RequestMapping (value = "/imgserver/image/{imgCode}/", method = RequestMethod.GET)
    public void getImage(HttpServletResponse response, @PathParam("imgCode") String imgCode){

        String mediaPath = imageService.getMediaPath(modelService.getModel(Image_.code.getName(), imgCode, Image.class));

        File img = new File(mediaPath);
        response.setContentType("image/png");
        try {
            InputStream in = new FileInputStream(img);
            try {
                IOUtils.copy(in, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @RequestMapping (value = "/imgserver/image/{imgCode}/preset/{presetCode}/", method = RequestMethod.GET)
    public void getImagePreset(HttpServletResponse response,
                               @PathParam("imgCode") String imgCode,
                               @PathParam("presetCode") String presetCode){

        String mediaPath = imageService.getMediaPath(modelService.getModel(Image_.code.getName(), imgCode, Image.class));

        File img = new File(mediaPath);
        response.setContentType("image/png");
        try {
            InputStream in = new FileInputStream(img);
            try {
                IOUtils.copy(in, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
