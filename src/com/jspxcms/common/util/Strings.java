package com.jspxcms.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

/**
 * 字符串工具类
 * 
 * @author liufang
 * 
 */
public abstract class Strings {
	private static Logger logger = LoggerFactory.getLogger(Strings.class);

	/**
	 * 字符串截断。编码大于127的字符作为占两个位置，否则占一个位置。
	 * 
	 * @param text
	 * @param length
	 * @param append
	 * @return
	 */
	public static String substring(String text, int length, String append) {
		if (StringUtils.isBlank(text) || text.length() < length) {
			return text;
		}
		int num = 0, i = 0, len = text.length();
		StringBuilder sb = new StringBuilder();
		for (; i < len; i++) {
			char c = text.charAt(i);
			if (c > 127) {
				num += 2;
			} else {
				num++;
			}
			if (num <= length * 2) {
				sb.append(c);
			}
			if (num >= length * 2) {
				break;
			}
		}
		if (i + 1 < len && StringUtils.isNotBlank(append)) {
			if (text.charAt(i) > 127) {
				sb.setLength(sb.length() - 1);
			} else {
				sb.setLength(sb.length() - 2);
			}
			sb.append(append);
		}
		return sb.toString();
	}

	public static String substring(String text, int length) {
		return substring(text, length, null);
	}

	public static String urlEncode(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// never
			throw new RuntimeException(e);
		}

	}

	public static String urlDecode(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// never
			throw new RuntimeException(e);
		}
	}

	public static void replace(StringBuilder sb, String searchString,
			String replacement) {
		int start = 0;
		int end = sb.indexOf(searchString, start);
		if (end == -1) {
			return;
		}
		int searchLength = searchString.length();
		int replaceLength = replacement.length();
		while (end != -1) {
			sb.replace(end, end + searchLength, replacement);
			start = end + replaceLength;
			end = sb.indexOf(searchString, start);
		}
	}

	public static String getTextFromHtml(String html, int length) {
		if (StringUtils.isBlank(html)) {
			return html;
		}
		if (length <= 0) {
			length = Integer.MAX_VALUE;
		}
		StringBuilder buff = new StringBuilder((int) (html.length() * 0.7));
		Lexer lexer = new Lexer(html);
		Node node;
		try {
			while ((node = lexer.nextNode()) != null && buff.length() < length) {
				if (node instanceof TextNode) {
					buff.append(HtmlUtils.htmlUnescape(node.getText()));
				}
			}
		} catch (ParserException e) {
			logger.error("parse html exception", e);
		}
		if (buff.length() > length) {
			buff.setLength(length);
		}
		return buff.toString();
	}

	public static String getTextFromHtml(String html) {
		return getTextFromHtml(html, Integer.MAX_VALUE);
	}

	// public static String getKeywords(String s, boolean useSmart) {
	// if (StringUtils.isBlank(s)) {
	// return "";
	// }
	// StringReader reader = new StringReader(s);
	// IKSegmenter iks = new IKSegmenter(reader, useSmart);
	// StringBuilder buff = new StringBuilder();
	// try {
	// Lexeme lexeme;
	// while ((lexeme = iks.next()) != null) {
	// buff.append(lexeme.getLexemeText()).append(',');
	// }
	// } catch (IOException e) {
	// logger.warn("StringReader error!", e);
	// }
	// if (buff.length() > 0) {
	// buff.setLength(buff.length() - 1);
	// }
	// return buff.toString();
	// }
	//
	// public static String getKeywords(String s) {
	// return getKeywords(s, true);
	// }
}
