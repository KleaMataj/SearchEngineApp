package com.index.app.Service;
import com.index.app.Model.DocumentModel;
import com.index.app.Util.IndexingServiceUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.function.valuesource.IDFValueSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexingService {

    public List<DocumentModel> queryIndexByContent(String queryToken) {
        return IndexingServiceUtil.searchIndexByContent(queryToken);
    }

    public static String addDoc(List<DocumentModel> documentList) {
        List<Document> documents = new ArrayList<>();
        for (DocumentModel d: documentList) {
            Document doc = new Document();

            doc.add(new StringField("index", d.index, Field.Store.YES));
            doc.add(new TextField("content", d.content, Field.Store.YES));
            documents.add(doc);
        }
        IndexingServiceUtil.addDoc(documents);
        return "200";
    }

}
