package ru.fil.gwt.shared.rest;

import org.fusesource.restygwt.client.DirectRestService;
import ru.fil.gwt.shared.dto.StringDto;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author fil.
 */
@Path("rest/simple")
public interface SimpleRest extends DirectRestService {

    @Path("hello")
    @POST
    StringDto sayHello(StringDto targetName);

}
