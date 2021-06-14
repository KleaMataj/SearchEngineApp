package com.index.app.Service;
import com.index.app.Model.DocumentModel;
import com.index.app.Util.IndexingServiceUtil;
import org.apache.lucene.document.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexingService {

    public List<DocumentModel> queryIndexByContent(String queryToken, int resultNumber) {
        return IndexingServiceUtil.searchIndexByContent(queryToken, resultNumber);
    }

    public static String addDoc(List<DocumentModel> documentList) {
        List<Document> documents = new ArrayList<>();
        for (DocumentModel d: documentList) {
            Document doc = new Document();

            doc.add(new StringField("id", d.id, Field.Store.YES));
            doc.add(new TextField("content", d.content, Field.Store.YES));
            documents.add(doc);
        }
        IndexingServiceUtil.addDoc(documents);
        return "200";
    }

}
