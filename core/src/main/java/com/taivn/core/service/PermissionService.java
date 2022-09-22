/**
 * 
 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Feb 27, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.core.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.taivn.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;

import com.taivn.common.service.I18nMessageService;

/**
 * The Class Permission.
 *
 * @author Taivn
 */
public class PermissionService {

    /** The i18nMessageService properties. */
    @Autowired
    private I18nMessageService i18nMessageService;

    /** The features. */
    private Map<Integer, PermissionDTO> features;

    /** The Constant ADMIN. */
    public static final int ADMIN = 0x00;

    // parameter.
    /** The Constant CREATE_PARAMETER. */
    public static final int READ_PARAMETER = 0x10;

    /** The Constant EDIT_PARAMETER. */
    public static final int WRITE_PARAMETER = 0x11;

    /** The Constant EDIT_PARAMETER. */
    public static final int DELETE_PARAMETER = 0x12;

    // scheme.
    /** The Constant CREATE_SCHEME. */
    public static final int READ_SCHEME = 0x20;

    /** The Constant EDIT_SCHEME. */
    public static final int WRITE_SCHEME = 0x21;

    /** The Constant SEARCH_SCHEME. */
    public static final int DELETE_SCHEME = 0x22;

    // process
    /** The Constant CREATE_PROCESS. */
    public static final int READ_PROCESS = 0x30;

    /** The Constant EDIT_PROCESS. */
    public static final int WRITE_PROCESS = 0x31;

    /** The Constant RERUN_SCHEME. */
    public static final int RERUN_SCHEME = 0x32;

    /** The Constant REVISE_SCHEME. */
    public static final int REVISE_SCHEME = 0x33;

    // role.
    /** The Constant CREATE_USERROLE. */
    public static final int READ_USERROLE = 0x40;

    /** The Constant EDIT_USERROLE. */
    public static final int WRITE_USERROLE = 0x41;

    /** The Constant SEARCH_USERROLE. */
    public static final int DELETE_USERROLE = 0x42;

    // user.
    /** The Constant CREATE_USER. */
    public static final int READ_USER = 0x50;

    /** The Constant EDIT_USER. */
    public static final int WRITE_USER = 0x51;

    /** The Constant DELETE_USER. */
    public static final int DELETE_USER = 0x52;

    /**
     * Gets the all permissions.
     *
     * @return the all permissions
     */
    private Map<Integer, PermissionDTO> getEntries() {
        if (this.features == null) {
            this.features = new HashMap<>();

            this.features.put(ADMIN, this.buildPermissionInfo(ADMIN));
            this.features.put(READ_PARAMETER, this.buildPermissionInfo(READ_PARAMETER));
            this.features.put(WRITE_PARAMETER, this.buildPermissionInfo(WRITE_PARAMETER));
            this.features.put(DELETE_PARAMETER, this.buildPermissionInfo(DELETE_PARAMETER));

            this.features.put(READ_SCHEME, this.buildPermissionInfo(READ_SCHEME));
            this.features.put(WRITE_SCHEME, this.buildPermissionInfo(WRITE_SCHEME));
            this.features.put(DELETE_SCHEME, this.buildPermissionInfo(DELETE_SCHEME));

            this.features.put(READ_PROCESS, this.buildPermissionInfo(READ_PROCESS));
            this.features.put(WRITE_PROCESS, this.buildPermissionInfo(WRITE_PROCESS));
            this.features.put(RERUN_SCHEME, this.buildPermissionInfo(RERUN_SCHEME));
            this.features.put(REVISE_SCHEME, this.buildPermissionInfo(REVISE_SCHEME));

            this.features.put(READ_USERROLE, this.buildPermissionInfo(READ_USERROLE));
            this.features.put(WRITE_USERROLE, this.buildPermissionInfo(WRITE_USERROLE));
            this.features.put(DELETE_USERROLE, this.buildPermissionInfo(DELETE_USERROLE));

            this.features.put(READ_USER, this.buildPermissionInfo(READ_USER));
            this.features.put(WRITE_USER, this.buildPermissionInfo(WRITE_USER));
            this.features.put(DELETE_USER, this.buildPermissionInfo(DELETE_USER));
        }

        return features;
    }

    /**
     * Gets the all permission.
     *
     * @return the all permission
     */
    public Collection<PermissionDTO> getAllPermission() {
        Map<Integer, PermissionDTO> entries = this.getEntries();

        if (entries != null) {
            return entries.values();
        }

        return Collections.emptyList();
    }

    /**
     * Gets the permission entry.
     *
     * @param id the id
     * @return the permission entry
     */
    public PermissionDTO getPermissionEntry(int id) {
        Map<Integer, PermissionDTO> items = this.getEntries();

        if (items != null && items.containsKey(id)) {
            return items.get(id);
        }

        return null;
    }

    /**
     * Builds the permission info.
     *
     * @param id the id
     * @return the permission DTO
     */
    private PermissionDTO buildPermissionInfo(int id) {
        PermissionDTO permission = new PermissionDTO();

        permission.setId(id);
        // permission.setEncryptedId(CryptoUtils.encrypt(String.valueOf(id),
        // cryptoService.getPermissionKey()));
        permission.setCode(this.buildCode(id));
        permission.setDisplayText(this.buildDisplayText(id));
        permission.setDescription(this.buildDescription(id));

        return permission;
    }

    /**
     * Builds the code.
     *
     * @param id the id
     * @return the string
     */
    private String buildCode(int id) {
        return String.format("0x%02X", id);
    }

    /**
     * Gets the feature text.
     *
     * @return the feature text
     */
    private String buildDisplayText(int id) {
        return i18nMessageService.getMessage(String.format("permission_0x%02X_text", id));
    }

    /**
     * Gets the feature text.
     *
     * @return the feature text
     */
    private String buildDescription(int id) {
        return i18nMessageService.getMessage(String.format("permission_0x%02X_description", id));
    }

}
