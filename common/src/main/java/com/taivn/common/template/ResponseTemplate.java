/**

 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 */
package com.taivn.common.template;

import com.taivn.common.enums.ErrorDetailEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets the data.
 *
 * @return the data
 */
@Getter

/**
 * Sets the data.
 *
 * @param data the new data
 */
@Setter

/**
 * To string.
 *
 * @return the java.lang. string
 */
@ToString
/**
 * Instantiates a new response template.
 */
@NoArgsConstructor
public class ResponseTemplate implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6316044415992367711L;

    /** The timestamp. */
    private Long timestamp;

    /** The status. */
    private Integer status;

    /** The message. */
    private String message;

    /** The errors. */
    private List<ErrorDetailEnum> errors;

    /** The id. */
    private Long id;

    /** The data. */
    private Object data;

    /**
     * Instantiates a new response template.
     *
     * @param status  the status
     * @param message the message
     * @param errors  the errors
     */
    public ResponseTemplate(Integer status, String message, List<ErrorDetailEnum> errors) {
        super();
        this.timestamp = System.currentTimeMillis();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Instantiates a new response template.
     *
     * @param status  the status
     * @param message the message
     * @param errors  the errors
     */
    public ResponseTemplate(HttpStatus status, String message, List<ErrorDetailEnum> errors) {
        super();
        this.timestamp = System.currentTimeMillis();
        this.status = status.value();
        this.message = message;
        this.errors = errors;
    }

    /**
     * Instantiates a new response template.
     *
     * @param status the status
     * @param error  the error
     */
    public ResponseTemplate(HttpStatus status, ErrorDetailEnum error) {
        super();
        this.timestamp = System.currentTimeMillis();
        this.status = status.value();
        this.message = error.getCode();
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    /**
     * Instantiates a new response template.
     *
     * @param status  the status
     * @param message the message
     */
    public ResponseTemplate(Integer status, String message) {
        this(status, message, null);
    }

    /**
     * Instantiates a new response template.
     *
     * @param status  the status
     * @param message the message
     */
    public ResponseTemplate(HttpStatus status, String message) {
        this.timestamp = System.currentTimeMillis();
        this.status = status.value();
        this.message = message;
    }

    /**
     * Instantiates a new response template.
     *
     * @param status the status
     */
    public ResponseTemplate(Integer status) {
        this(status, null);
    }

    /**
     * Instantiates a new response template.
     *
     * @param status the status
     */
    public ResponseTemplate(HttpStatus status) {
        this.timestamp = System.currentTimeMillis();
        this.status = status.value();
    }

    /**
     * Instantiates a new response template.
     *
     * @param id      the id
     * @param status  the status
     * @param message the message
     */
    public ResponseTemplate(Long id, HttpStatus status, String message) {
        super();
        this.timestamp = System.currentTimeMillis();
        this.status = status.value();
        this.message = message;
        this.id = id;
    }

    /**
     * Instantiates a new response template.
     *
     * @param id        the id
     * @param status    the status
     * @param message   the message
     * @param timestamp the timestamp
     */
    public ResponseTemplate(Long id, Integer status, String message, Long timestamp) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.id = id;
    }

    /**
     * Instantiates a new response template.
     *
     * @param data    the data
     * @param status  the status
     * @param message the message
     */
    public ResponseTemplate(Object data, HttpStatus status, String message) {
        super();
        this.timestamp = System.currentTimeMillis();
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
