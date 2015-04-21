package com.jspxcms.common.fulltext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.common.orm.QuerydslUtils;
import com.mysema.query.lucene.LuceneQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;

/**
 * 默认全文检索模板
 * 
 * 每次查询前执行SearcherManager.maybeRefresh();
 * 
 * @author liufang
 * 
 */
public class DefaultLuceneIndexTemplate implements LuceneIndexTemplate,
		InitializingBean {
	private boolean isAutoCommit = false;
	private IndexWriter indexWriter;

	private SearcherManager searcherManager;

	public DefaultLuceneIndexTemplate() {
	}

	public DefaultLuceneIndexTemplate(IndexWriter indexWriter,
			SearcherManager searcherManager) {
		this.indexWriter = indexWriter;
		this.searcherManager = searcherManager;
	}

	public List<String> list(Query query, String field, Limitable limitable,
			Sort sort) {
		try {
			IndexSearcher searcher = null;
			try {
				searcherManager.maybeRefresh();
				searcher = searcherManager.acquire();
				int n = limitable.getLastResult();
				n = n <= 0 ? 2000 : n;
				if (sort == null) {
					sort = new Sort();
				}
				TopDocs results = searcher.search(query, n, sort);
				int length = results.scoreDocs.length;
				List<String> list = new ArrayList<String>(length);
				for (ScoreDoc hit : results.scoreDocs) {
					list.add(searcher.doc(hit.doc).get(field));
				}
				return list;
			} finally {
				if (searcher != null) {
					searcherManager.release(searcher);
				}
			}
		} catch (Exception e) {
			throw new LuceneException("Error during searching.", e);
		}
	}

	public Page<String> page(Query query, String field, Pageable pageable,
			Sort sort) {
		try {
			IndexSearcher searcher = null;
			try {
				searcherManager.maybeRefresh();
				searcher = searcherManager.acquire();
				int n = pageable.getOffset() + pageable.getPageSize();
				if (sort == null) {
					sort = new Sort();
				}
				TopDocs results = searcher.search(query, n, sort);
				int length = results.scoreDocs.length;
				int size = length - pageable.getOffset();
				List<String> content;
				if (size > 0) {
					content = new ArrayList<String>(size);
					ScoreDoc hit;
					for (int i = pageable.getOffset(); i < length; i++) {
						hit = results.scoreDocs[i];
						content.add(searcher.doc(hit.doc).get(field));
					}
				} else {
					content = Collections.emptyList();
				}
				int total = results.totalHits;
				return new PageImpl<String>(content, pageable, total);
			} finally {
				if (searcher != null) {
					searcherManager.release(searcher);
				}
			}
		} catch (Exception e) {
			throw new LuceneException("Error during searching.", e);
		}
	}

	public List<Document> list(Predicate predicate,
			EntityPath<Document> entityPath, Limitable limitable) {
		try {
			IndexSearcher searcher = null;
			try {
				searcherManager.maybeRefresh();
				searcher = searcherManager.acquire();
				LuceneQuery query = new LuceneQuery(searcher);
				query.where(predicate);
				QuerydslUtils.applySorting(query, entityPath,
						limitable.getSort());
				Integer firstResult = limitable.getFirstResult();
				if (firstResult != null && firstResult > 0) {
					query.offset(firstResult);
				}
				Integer maxResults = limitable.getMaxResults();
				if (maxResults != null && maxResults > 0) {
					query.limit(maxResults);
				}
				return query.list();
			} finally {
				if (searcher != null) {
					searcherManager.release(searcher);
				}
			}
		} catch (Exception e) {
			throw new LuceneException("Error during searching.", e);
		}
	}

	public Page<Document> page(Predicate predicate,
			EntityPath<Document> entityPath, Pageable pageable) {
		try {
			IndexSearcher searcher = null;
			try {
				searcherManager.maybeRefresh();
				searcher = searcherManager.acquire();
				LuceneQuery query = new LuceneQuery(searcher);
				long total = query.count();
				List<Document> content;
				if (total > pageable.getOffset()) {
					query.offset(pageable.getOffset());
					query.limit(pageable.getPageSize());
					QuerydslUtils.applySorting(query, entityPath,
							pageable.getSort());
					content = query.list();
				} else {
					content = Collections.emptyList();
				}
				Page<Document> page = new PageImpl<Document>(content, pageable,
						total);
				return page;
			} finally {
				if (searcher != null) {
					searcherManager.release(searcher);
				}
			}
		} catch (Exception e) {
			throw new LuceneException("Error during searching.", e);
		}
	}

	public void addDocument(Document document) {
		try {
			indexWriter.addDocument(document);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during adding a document.", e);
		}
	}

	public void addDocuments(Collection<Document> documents) {
		try {
			indexWriter.addDocuments(documents);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during adding a document.", e);
		}

	}

	public void updateDocument(Term term, Document document) {
		try {
			indexWriter.updateDocument(term, document);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during updating a document.", e);
		}
	}

	public void deleteDocuments(Term... terms) {
		try {
			indexWriter.deleteDocuments(terms);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during deleting a document.", e);
		}
	}

	public void deleteDocuments(Term term) {
		try {
			indexWriter.deleteDocuments(term);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during deleting a document.", e);
		}
	}

	public void deleteDocuments(Query... queries) {
		try {
			indexWriter.deleteDocuments(queries);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during deleting a document.", e);
		}
	}

	public void deleteDocuments(Query query) {
		try {
			indexWriter.deleteDocuments(query);
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during deleting a document.", e);
		}
	}

	public void deleteAll() {
		try {
			indexWriter.deleteAll();
			if (isAutoCommit) {
				indexWriter.commit();
			}
		} catch (Exception e) {
			throw new LuceneException("Error during deleting a document.", e);
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (indexWriter == null) {
			throw new IllegalArgumentException("indexFactory is required");
		}
		if (searcherManager == null) {
			throw new IllegalArgumentException("indexFactory is required");
		}
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		this.indexWriter = indexWriter;
	}

	public void setSearcherManager(SearcherManager searcherManager) {
		this.searcherManager = searcherManager;
	}

	public void setAutoCommit(boolean isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
	}

}
