/**
 *
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 *                                                                                
 */
package com.taivn.common.enums;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorCodes.
 */
public final class ErrorCodes {

    /** The Constant INVALID_ARGUMENT. */
    public static final String INVALID_ARGUMENT = "E0000001";

    /** The Constant INVALID_RANGE. */
    public static final String INVALID_RANGE = "E0000002";

    /** The Constant INVALID_FORMAT. */
    public static final String INVALID_FORMAT = "E0000003";

    /** The Constant NOT_FOUND. */
    public static final String NOT_FOUND = "E0000004";

    /** The Constant FORBIDDEN. */
    public static final String FORBIDDEN = "E0000005";

    /** The Constant NULL_POINTER. */
    public static final String NULL_POINTER = "E0000006";

    /** The Constant EXPORT_FAILED. */
    public static final String EXPORT_FAILED = "E0000007";

    /** The Constant ENCRYPTION_FAILED. */
    public static final String ENCRYPTION_FAILED = "E000101";

    /** The Constant DECRYPTION_FAILED. */
    public static final String DECRYPTION_FAILED = "E000102";

    /** The Constant GENERIC. */
    public static final String GENERIC = "E0000999";

    /** ==============================================================. */

    /** common */
    /** uc_common_delete_info_001 */
    public static final String UC_COMMON_DELETE_INFO_001 = "uc_common_delete_info_001";

    /** The Constant uc_common_delete_warn_001. */
    public static final String UC_COMMON_DELETE_WARN_001 = "uc_common_delete_warn_001";

    /** The Constant UC_COMMON_REQUEST_INVALID_WARN_002. */
    public static final String UC_COMMON_REQUEST_INVALID_WARN_002 = "uc_common_request_invalid_warn_002";

    /** The Constant UC_COMMON_OBJECT_NOT_EXIST_WARN_003. */
    public static final String UC_COMMON_OBJECT_NOT_EXIST_WARN_003 = "uc_common_object_not_exist_warn_003";

    /** The Constant uc1_info_001. */
    public static final String uc1_info_001 = "uc1_info_001";

    /** The Constant uc_user_role_info_001. */

    /** The Constant REQUEST_NOT_FOUND. */
    public static final String REQUEST_NOT_FOUND = "uc_permission_warn_request_not_found";

    /** The Constant ACCESS_DENIED. */
    public static final String ACCESS_DENIED = "uc_permission_warn_access_denied";

    /** The Constant UNAUTHORIZED. */
    public static final String UNAUTHORIZED = "uc_permission_warn_unauthorized";

    /** The Constant TOKEN_EXPIRED. */
    public static final String TOKEN_EXPIRED = "uc_permission_warn_token_expired";

    /** The Constant REQUEST_NOT_FOUND_ID. */
    public static final long REQUEST_NOT_FOUND_ID = 1000;

    /** The Constant ACCESS_DENIED_ID. */
    public static final long ACCESS_DENIED_ID = 1001;

    /** The Constant UNAUTHORIZED_ID. */
    public static final long UNAUTHORIZED_ID = 1002;

    /** The Constant TOKEN_EXPIRED_ID. */
    public static final long TOKEN_EXPIRED_ID = 1003;

    /** Manufacturing */
    /** initial error */
    public static final String MF_INITIAL_ERROR = "mf_initial_error";

    /** initial error */
    public static final String MF_CREATE_SCENARIOS_ERROR = "mf_create_scenario_error";

    /** table does not exists error */
    public static final String MF_TABLE_EXISTS_ERROR = "mf_table_exists_error";
    
    /** configuration info not exists error */
    public static final String MF_CONFIGURATION_EXISTS_ERROR = "mf_config_exists_error";
    
    /**
     * Instantiates a new error codes.
     */
    private ErrorCodes() {
        // Do nothing.
    }
}
