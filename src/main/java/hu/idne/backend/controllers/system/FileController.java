package hu.idne.backend.controllers.system;

import hu.idne.backend.models.system.HttpResponseFactory;
import hu.idne.backend.services.system.FileStorageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/public/file")
public class FileController {

    private final FileStorageService documentStorageService;

    @Autowired
    public FileController(@Qualifier("getFileStorageService") FileStorageService storageService) {
        this.documentStorageService = storageService;
    }

    @ApiOperation("Get file resource from file storage")
    @GetMapping(path = "/get")
    public HttpEntity<byte[]> getFile(@RequestParam(name = "folder") String folder, @RequestParam(name = "filename") String fileName, @RequestParam(name = "inline", required = false, defaultValue = "false") boolean showInline) throws Exception {
        if (showInline) {
            return HttpResponseFactory.makePdfInlineHttpResponse(fileName, documentStorageService.getFile(folder, fileName));
        } else {
            return HttpResponseFactory.makePictureHttpResponse(fileName, documentStorageService.getFile(folder, fileName));
        }
    }
}
