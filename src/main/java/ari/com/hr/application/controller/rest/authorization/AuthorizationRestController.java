package ari.com.hr.application.controller.rest.authorization;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dto.GlobalDto;
import ari.com.hr.application.dto.SysAuthorizationDto;
import ari.com.hr.application.model.SysAuthorization;
import java.util.ArrayList;
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

@RestController
@RequestMapping("/admin/v1/api/authorization")
public class AuthorizationRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysAuthorizationDao dsSysAuthorization;

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<GlobalDto> index(@PathVariable("id") Long id,
            @RequestParam(value = "vInsert") boolean vInsert,
            @RequestParam(value = "vUpdate") boolean vUpdate,
            @RequestParam(value = "vDelete") boolean vDelete,
            @RequestParam(value = "vDisable") boolean vDisable) {
        int inUpdate = dsSysAuthorization.updateAuthorization(id, vInsert, vUpdate, vDelete, vDisable);

        log.debug(id + "inUpdate" + inUpdate);

        GlobalDto globalDto = new GlobalDto();
        globalDto.setId(id);
        globalDto.setCount(inUpdate);

        return new ResponseEntity(globalDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/addMenu/{idRoles}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public SysAuthorizationDto authorizationAddMenu(@PathVariable("idRoles") Long idRole,
            @RequestParam("vInsert") boolean vInsert,
            @RequestParam("vUpdate") boolean vUpdate,
            @RequestParam("vDelete") boolean vDelete,
            @RequestParam("vDisable") boolean vDisable,
            @RequestParam("modelMenuId") Long MenuId,
            @RequestParam("modelParentMenuId") Long parentMenuId) {
        return saveDataMenu(idRole, vInsert, vUpdate,
                vDelete, vDisable, MenuId, parentMenuId);
    }

    @RequestMapping(value = "/deleteMenu/{idAuthorization}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GlobalDto> deleteMenu(@PathVariable("idAuthorization") Long id) {
        dsSysAuthorization.delete(id);
        GlobalDto globalDto = new GlobalDto();
        globalDto.setId(id);

        return new ResponseEntity(globalDto, HttpStatus.OK);
    }

    private SysAuthorizationDto saveDataMenu(Long idRole, boolean vInsert,
            boolean vUpdate, boolean vDelete,
            boolean vDisable, Long MenuId,
            Long parentMenuId) {

        SysAuthorization dataAuthorization = new SysAuthorization();
        log.debug("-add new menu on id " + idRole
                + ", menuId : " + MenuId
                + ", parentId " + parentMenuId
                + " " + vInsert + " " + vUpdate + " " + vDelete + " " + vDisable);
        dataAuthorization.setSysMenu(MenuId);

        if (parentMenuId == null) {
            dataAuthorization.setParent(null);
        } else {
            dataAuthorization.setParent(parentMenuId);
        }

        dataAuthorization.setSysRoles(idRole);
        dataAuthorization.setIsDelete(vDelete);
        dataAuthorization.setIsInsert(vInsert);
        dataAuthorization.setIsUpdate(vUpdate);
        dataAuthorization.setDisabled(vDisable);
        dataAuthorization.setIsRead(true);

        dataAuthorization = dsSysAuthorization.save(dataAuthorization);
        log.debug("new id menu after add : " + dataAuthorization.getId() + "--");

        SysAuthorizationDto dataAuthorizations = dsSysAuthorization.getDataAuthorizationById(dataAuthorization.getId());
        log.debug("new id menu after get :" + dataAuthorizations.getId() + "--");
        log.debug("menu name after add : " + dataAuthorizations.getMenuName());

        return dataAuthorizations;
    }
}
