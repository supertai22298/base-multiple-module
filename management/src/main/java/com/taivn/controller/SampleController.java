/**
 * 
 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Feb 27, 2022     ********           Taivn            Initialize
 */
package com.taivn.controller;

import com.taivn.annotations.Permitted;
import com.taivn.common.base.controller.BaseController;
import com.taivn.common.enums.ErrorCodes;
import com.taivn.common.template.ResponseTemplate;
import com.taivn.core.converter.SampleConverter;
import com.taivn.core.exception.InvalidRequestException;
import com.taivn.core.service.SampleService;
import com.taivn.dto.CreateSampleDTO;
import com.taivn.dto.SampleDTO;
import com.taivn.entity.table.SampleTbl;
import com.taivn.repository.SampleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Taivn
 *
 */
@RestController
@RequestMapping("/api/sample")
public class SampleController extends
        BaseController<Integer, SampleTbl, SampleDTO, SampleRepository, SampleConverter, SampleService> {

    /**
     * Login.
     *
     * @param filterDto the login dto
     * @return the response entity
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Permitted(isPublic = true)
    public ResponseEntity<Object> createScenarios(@RequestBody final CreateSampleDTO filterDto) {
        Object result = null;

        try {
            result = this.dataService.createSampleRecord(filterDto);
        } catch (InvalidRequestException iEx) {
        	result = new ResponseTemplate(HttpStatus.BAD_REQUEST,
                    String.format(i18nMessageService.getMessage(iEx.getErrorCode()), iEx.getMessage()));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            result = new ResponseTemplate(HttpStatus.BAD_REQUEST,
                    i18nMessageService.getMessage(ErrorCodes.SAMPLE_CREATE_SAMPLE_ERROR));
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Get data Simulation summary
     *
     * @param sampleId
     * @return the response entity
     */
    @GetMapping(value = "/{sampleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Permitted(isPublic = true)
    public ResponseEntity<Object> getSimulationSummary(@PathVariable("sampleId") final String sampleId) {
        Object result = null;

        try {
            result = this.dataService.getSampleById(sampleId);
        } catch (Exception ex) {
            result = new ResponseTemplate(HttpStatus.BAD_REQUEST,
                    i18nMessageService.getMessage(ErrorCodes.SAMPLE_CREATE_SAMPLE_ERROR));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
