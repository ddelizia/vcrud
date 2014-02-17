package org.ddelizia.vcrud.core.media.image.processor.resize;

import org.ddelizia.vcrud.core.basic.collection.map.MappingRegistrar;
import org.ddelizia.vcrud.core.basic.collection.map.MappingRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class ResizeProcessorRegistar extends MappingRegistrar<ResizeEnum, ResizeProcessor> {

    public ResizeProcessorRegistar(MappingRegistry<ResizeEnum, ResizeProcessor> registry) {
        super(registry);
    }

}
