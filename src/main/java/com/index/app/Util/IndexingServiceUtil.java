package com.index.app.Util;

import com.index.app.Model.DocumentModel;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndexingServiceUtil {


    private static StandardAnalyzer analyzer;
    private static  IndexWriter indexWriter ;

    static{
        try {
            analyzer =  new StandardAnalyzer();
            AppState appState = new AppState();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(AppState.getIndex(), iwc);

            indexWriter.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void cleanup(){
        try {
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<DocumentModel> searchIndexByContent(String queryString){
        List<DocumentModel> documentResultList = new ArrayList<>();
        IndexReader indexReader = null;

        try {

            indexReader = DirectoryReader.open(AppState.getIndex());
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            Query query = new QueryParser("content", analyzer).parse(queryString);
            TopDocs docs = indexSearcher.search(query, 10);
            ScoreDoc[] hits = docs.scoreDocs;


            long total = docs.totalHits;;

            for(int i=0;i<hits.length;++i) {
                int docId = hits[i].doc;
                Document d = indexSearcher.doc(docId);

                DocumentModel documentModel = new DocumentModel();
                documentModel.setContent(d.get("content"));
                documentModel.setIndex(d.get("index"));

                documentResultList.add(documentModel);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                indexReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return documentResultList;
    }

    public static void addDoc(List<Document> documents) {

        try {
            for (Document document:documents) {

                Term term = new Term("index", document.get("index"));
                System.out.println("Deleting documents with field '" + term.field() + "' with text '" + term.text() + "'");
                indexWriter.deleteDocuments(term);

                System.out.println("::::::::::::::" + "Adding Document...");
                indexWriter.addDocument(document);
                System.out.println("::::::::::::::" + "Document Added...");

            }
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
