package ari.com.hr.application.controller.rest.authorization;

import ari.com.hr.application.dao.SysAuthorizationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/api/authorization")
public class AuthorizationRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysAuthorizationDao dsSysAuthorization;

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void index(@PathVariable("id") Integer id,
            @RequestParam(value = "vInsert") boolean vInsert,
            @RequestParam(value = "vUpdate") boolean vUpdate,
            @RequestParam(value = "vDelete") boolean vDelete,
            @RequestParam(value = "vDisable") boolean vDisable) {

        int inUpdate = dsSysAuthorization.updateAuthorization(Long.valueOf(id), vInsert, vUpdate, vDelete, vDisable);
        log.debug("-------------------------------------------------" + Long.valueOf(id) + "inUpdate" + inUpdate );
    }
}
