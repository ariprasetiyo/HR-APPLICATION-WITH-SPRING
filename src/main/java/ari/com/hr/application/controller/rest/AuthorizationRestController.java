package ari.com.hr.application.controller.rest;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dto.GlobalDto;
import ari.com.hr.application.dto.SysAuthorizationDto;
import ari.com.hr.application.dto.SysRolesDto;
import ari.com.hr.application.services.AuthorizationService;
import ari.com.hr.application.util.Logs;

@RestController
@RequestMapping("/admin/v1/api/authorization")
public class AuthorizationRestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysAuthorizationDao dsSysAuthorization;
    
    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<GlobalDto> index(@PathVariable("id") Long id,
            @RequestParam(value = "vInsert") boolean vInsert,
            @RequestParam(value = "vUpdate") boolean vUpdate,
            @RequestParam(value = "vDelete") boolean vDelete,
            @RequestParam(value = "vDisable") boolean vDisable) {
        int inUpdate = dsSysAuthorization.updateAuthorization(id, vInsert, vUpdate, vDelete, vDisable);

        Logs.logDebug(logger,"{} inUpdate {}", id, inUpdate);

        GlobalDto globalDto = new GlobalDto();
        globalDto.setId(id);
        globalDto.setCount(inUpdate);

        return new ResponseEntity<GlobalDto>(globalDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/addMenu/{idRoles}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SysAuthorizationDto authorizationAddMenu(@PathVariable("idRoles") Long idRole,
            @RequestParam("vInsert") boolean vInsert,
            @RequestParam("vUpdate") boolean vUpdate,
            @RequestParam("vDelete") boolean vDelete,
            @RequestParam("vDisable") boolean vDisable,
            @RequestParam("modelMenuId") Long MenuId,
            @RequestParam("modelParentMenuId") Long parentMenuId) {
        return authorizationService.saveDataMenu(idRole, vInsert, vUpdate,
                vDelete, vDisable, MenuId, parentMenuId);
    }
       
	@RequestMapping(value = "/list/{idRole}", method = RequestMethod.POST)
	public ResponseEntity<List<SysAuthorizationDto>> viewAuthorizationList(@PathVariable("idRole") Long idRole) {
		return new ResponseEntity<List<SysAuthorizationDto>>(authorizationService.getAuthorizationList(idRole),
				HttpStatus.OK);
	}
	
    @RequestMapping(value = "/deleteMenu/{idAuthorization}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GlobalDto> deleteMenu(@PathVariable("idAuthorization") Long id) {
        dsSysAuthorization.delete(id);
        GlobalDto globalDto = new GlobalDto();
        globalDto.setId(id);
        return new ResponseEntity<GlobalDto>(globalDto, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/viewRoles/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SysRolesDto viewRoles(@RequestParam("idRole") Long idRoles) {
        //logger.debug("view roles {}", idRoles);
        return null;
    }

}
