package com.index.app.Controller;

import com.index.app.Model.DocumentModel;
import com.index.app.Model.QueryModel;
import com.index.app.Service.IndexingService;
import com.index.app.Util.IndexingServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("indexing")
public class IndexController {

    @Autowired
    IndexingService indexingService;

    @PostMapping("/create")
    public String addDocument(@RequestBody List<DocumentModel> documentModelList){
        return indexingService.addDoc(documentModelList);
    }

    @PostMapping("/query")
    public List<DocumentModel> getIndexByQueryingContent(@RequestBody QueryModel query){
        return indexingService.queryIndexByContent(query.content);
    }

}
