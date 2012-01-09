package com.eleven7.imall.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.common.CommonConfig;
import com.eleven7.imall.dao.base.PageBean;

@Service
public class LuceneServiceImpl implements ILuceneService {

	Log log = LogFactory.getLog(this.getClass());
	//private static String INDEX_PATH = CommonConfig.getWebRootPath() + "index";
	private static String PRODUCT_INDEX_PATH = CommonConfig.getWebRootPath() + "index/product"; //"E:\\index\\";
	private static int MAX_INDEX_FIELD_LENGTH = 10000000;
	private static int MAX_NUM = 100;

	public boolean buildProductIndex(Product p) {
		String productPath = PRODUCT_INDEX_PATH;
		File productDir = new File(productPath);
		boolean retValue = true;
		IndexWriter writer = null;
		try {
			if (productDir.exists()) {
				writer = new IndexWriter(productPath, new StandardAnalyzer(),
						false);
			} else {
				writer = new IndexWriter(productPath, new StandardAnalyzer(),
						true);
			}

			writer.setMaxFieldLength(MAX_INDEX_FIELD_LENGTH);
			// 将参数转化为索引记录
			// id name shortdesc description
			Document doc = new Document();

			doc.add(new Field("id", String.valueOf(p.getId()), Field.Store.YES,
					Field.Index.UN_TOKENIZED));
			if (p.getName() != null) {
				doc.add(new Field("name", p.getName(), Field.Store.YES,
						Field.Index.TOKENIZED));
			} else {
				doc.add(new Field("name", "", Field.Store.YES,
						Field.Index.TOKENIZED));
			}

			if (p.getShortDesc() != null) {
				doc.add(new Field("short_desc", p.getShortDesc(),
						Field.Store.YES, Field.Index.TOKENIZED));
			} else {
				doc.add(new Field("short_desc", "", Field.Store.YES,
						Field.Index.TOKENIZED));
			}

			if (p.getDescription() != null) {
				doc.add(new Field("long_desc", p.getDescription(),
						Field.Store.YES, Field.Index.TOKENIZED));
			} else {
				doc.add(new Field("long_desc", "", Field.Store.YES,
						Field.Index.TOKENIZED));
			}
			doc.add(new Field("price",String.valueOf(p.getPrice()),Field.Store.YES,Field.Index.NO));
			Term term = new Term("id", String.valueOf(p.getId()));
			writer.updateDocument(term, doc);
			writer.optimize();
		} catch (IOException ex) {
			ex.printStackTrace();
			retValue = false;
		} catch (Exception ex) { // 其他，如空指针(NullPointerException)等
			ex.printStackTrace();
			retValue = false;
		} finally {
			// 关闭索引器
			try {
				if (writer != null)
					writer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				retValue = false;
			} catch (Exception ex) {
				ex.printStackTrace();
				retValue = false;
			}
		}
		return retValue;

	}

	public List<Product> queryProduct(String searchKey, PageBean pb) {
		List<Product> list = new ArrayList<Product>();
		try {
			File productDir = new File(PRODUCT_INDEX_PATH);
			//如果索引目录不存在，直接返回
			if(!productDir.exists())
			{
				return list;
			}
			// 根据索引位置建立IndexSearcher
			IndexSearcher searcher = new IndexSearcher(PRODUCT_INDEX_PATH);
			// 查询条件之间的关系
			// BooleanClause.Occur[] flags = new
			// BooleanClause.Occur[]{BooleanClause.Occur.MUST,BooleanClause.Occur.MUST};
			StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

			QueryParser queryParser = new MultiFieldQueryParser(new String[] {
					"name", "short_desc", "long_desc" }, standardAnalyzer);

			queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query = null;
			try {
				query = queryParser.parse(searchKey);
			} catch (ParseException ex) {
				log.error("" + ex);
				return list;
			}
			TopDocCollector collection = new TopDocCollector(MAX_NUM);
			try {
				searcher.search(query, collection);
			} catch (IOException ex) {
				log.error("" + ex);
				return list;
			}

			Document doc = null;
			int n = collection.getTotalHits();
			log.info("lucene一下，总共找到" + n + "个以下结果：");
			pb.setTotal(n);

			ScoreDoc[] docs = collection.topDocs().scoreDocs;

			// [start,end)
			int start = (pb.getPage() - 1) * pb.getSize();
			int end = start + pb.getSize();

			for (int i = start; i < end && i < n; i++) {
				try {
					doc = searcher.doc(docs[i].doc);
					Product p = new Product();
					Highlighter highlighter = new Highlighter(new QueryScorer(
							query));
					highlighter.setTextFragmenter(new SimpleFragmenter(50));

					p.setId(Integer.parseInt(doc.get("id")));
					p.setName(doc.get("name"));
					p.setShortDesc(doc.get("short_desc"));
					p.setDescription(doc.get("long_desc"));
					p.setPrice(Double.parseDouble(doc.get("price")));
					list.add(p);
				} catch (IOException ex) {
					log.error("" + ex);
				}
			}

		} catch (IOException e) {
			log.error("" + e);
		}
		return list;
	}

}
