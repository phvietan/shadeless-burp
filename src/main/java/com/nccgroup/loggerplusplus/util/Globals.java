package com.nccgroup.loggerplusplus.util;

import com.nccgroup.loggerplusplus.logentry.Helper;
import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Pattern;

import static com.nccgroup.loggerplusplus.logentry.LogEntryField.*;

public class Globals {
    public static final String APP_SIGNATURE = Helper.generateSignature();
    public static final String APP_NAME = "Shadeless Burp";
    public static final String VERSION = "3.19.1";
    public static final String TWITTER_URL = "https://twitter.com/CoreyD97";
    public static final String IRSDL_TWITTER_URL = "https://twitter.com/irsdl";
    public static final String NCC_TWITTER_URL = "https://twitter.com/nccgroup";
    public static final String GITHUB_FEATURE_URL = "https://github.com/nccgroup/LoggerPlusPlus/issues/new?assignees=CoreyD97&labels=enhancement&template=feature_request.md&title=";
    public static final String GITHUB_BUG_URL = "https://github.com/nccgroup/LoggerPlusPlus/issues/new?assignees=CoreyD97&labels=bug&template=bug_report.md&title=v" + VERSION + "%20-%20Bug:%20YOUR_TITLE_HERE";

    //Preferences Keys
    public static final String PREF_LOG_TABLE_SETTINGS = "tabledetailsjson";
    public static final String PREF_LOG_LEVEL = "logLevel";
    public static final String PREF_LOG_TO_CONSOLE = "logToConsole";
    public static final String PREF_UPDATE_ON_STARTUP = "updateonstartup";
    public static final String PREF_ENABLED = "enabled";
    public static final String PREF_RESTRICT_TO_SCOPE = "restricttoscope";
    public static final String PREF_LOG_GLOBAL = "logglobal";
    public static final String PREF_LOG_PROXY = "logproxy";
    public static final String PREF_LOG_SPIDER = "logspider";
    public static final String PREF_LOG_INTRUDER = "logintruder";
    public static final String PREF_LOG_SCANNER = "logscanner";
    public static final String PREF_LOG_REPEATER = "logrepeater";
    public static final String PREF_LOG_SEQUENCER = "logsequencer";
    public static final String PREF_LOG_EXTENDER = "logextender";
    public static final String PREF_LOG_TARGET_TAB = "logtargettab";
    public static final String PREF_COLOR_FILTERS = "colorfilters";
    public static final String PREF_TAG_FILTERS = "tagfilters";
    public static final String PREF_SAVED_FILTERS = "savedfilters";
    public static final String PREF_SORT_COLUMN = "sortcolumn";
    public static final String PREF_SORT_ORDER = "sortorder";
    public static final String PREF_RESPONSE_TIMEOUT = "responsetimeout";
    public static final String PREF_MAXIMUM_ENTRIES = "maximumentries";
    public static final String PREF_LAYOUT = "layout";
    public static final String PREF_MESSAGE_VIEW_LAYOUT = "msgviewlayout";
    public static final String PREF_SEARCH_THREADS = "searchthreads";
    public static final String PREF_CODE_NAME = "codeName";
    public static final String PREF_AUTO_IMPORT_PROXY_HISTORY = "autoimportproxyhistory";
    public static final String PREF_SHADELESS_PACKETS_URL = "shadelessPacketsURL";
    public static final String PREF_SHADELESS_FILES_URL = "shadelessFilesURL";
    public static final String PREF_SHADELESS_FILES_CHECK_URL = "shadelessFilesCheckURL";
    public static final String PREF_SHADELESS_PROJECT = "shadelessProject";
    public static final String PREF_SHADELESS_DELAY = "esDelay";
    public static final String PREF_LOG_OTHER_LIVE = "otherToolLiveLogging";
    public static final String PREF_FILTER_HISTORY = "filterHistory";
    public static final String PREF_AUTO_SAVE = "autoSave";
    public static final String PREF_AUTO_SCROLL = "autoScroll";
    public static final String PREF_GREP_HISTORY = "grepHistory";
    public static final String PREF_SAVED_FIELD_SELECTIONS = "savedFieldSelections";
    public static final String PREF_COLUMNS_VERSION = "columnsVersion";

    public static final String DEFAULT_COLOR_FILTERS_JSON = "{\"2add8ace-b652-416a-af08-4d78c5d22bc7\":{\"uid\":\"2add8ace-b652-416a-af08-4d78c5d22bc7\"," +
            "\"filter\":{\"filter\":\"Request.Complete == False\"},\"filterString\":\"Request.Complete == False\",\"backgroundColor\":{\"value\":-16777216,\"falpha\":0.0}," +
            "\"foregroundColor\":{\"value\":-65536,\"falpha\":0.0},\"enabled\":true,\"modified\":false,\"shouldRetest\":true,\"priority\":1}}";

    public static final int CURRENT_COLUMN_VERSION = 5;
    private static int colOrder = 0;
    public static final String DEFAULT_LOG_TABLE_COLUMNS_JSON = new StringBuilder().append("[")
            .append("{'id':" + NUMBER + ",'name':'Number','defaultVisibleName':'#','visibleName':'#','preferredWidth':65,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(NUMBER.getDescription()) + "'},")
            .append("{'id':" + REQUEST_PACKET_ID + ",'name':'PacketId','defaultVisibleName':'PacketId','visibleName':'PacketId','preferredWidth':65,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(REQUEST_PACKET_ID.getDescription()) + "'},")
            .append("{'id':" + STATIC_SCORE + ",'name':'StaticScore','defaultVisibleName':'StaticScore','visibleName':'StaticScore','preferredWidth':65,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(STATIC_SCORE.getDescription()) + "'},")
            .append("{'id':" + METHOD + ",'name':'Method','defaultVisibleName':'Method','visibleName':'Method','preferredWidth':65,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(METHOD.getDescription()) + "'},")
            .append("{'id':" + PROTOCOL + ",'name':'Protocol','defaultVisibleName':'Protocol','visibleName':'Protocol','preferredWidth':80,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(PROTOCOL.getDescription()) + "'},")
            .append("{'id':" + HOSTNAME + ",'name':'Hostname','defaultVisibleName':'Host Name','visibleName':'Host Name','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(HOSTNAME.getDescription()) + "'},")
            .append("{'id':" + PORT + ",'name':'TargetPort','defaultVisibleName':'Port','visibleName':'Port','preferredWidth':50,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(PORT.getDescription()) + "'},")
            .append("{'id':" + HOST + ",'name':'Host','defaultVisibleName':'Host','visibleName':'Host','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(HOST.getDescription()) + "'},")
            .append("{'id':" + PATH + ",'name':'Path','defaultVisibleName':'Path','visibleName':'Path','preferredWidth':250,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(PATH.getDescription()) + "'},")
            .append("{'id':" + EXTENSION + ",'name':'UrlExtension','defaultVisibleName':'Extension','visibleName':'Extension','preferredWidth':70,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(EXTENSION.getDescription()) + "'},")
            .append("{'id':" + QUERY + ",'name':'Query','defaultVisibleName':'Query','visibleName':'Query','preferredWidth':250,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(QUERY.getDescription()) + "'},")
            .append("{'id':" + URL + ",'name':'Url','defaultVisibleName':'URL','visibleName':'URL','preferredWidth':250,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(URL.getDescription()) + "'},")
            .append("{'id':" + HASPARAMS + ",'name':'Has Params','defaultVisibleName':'Has Params','visibleName':'Has Params','preferredWidth':75,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(HASPARAMS.getDescription()) + "'},")
            .append("{'id':" + STATUS + ",'name':'Status','defaultVisibleName':'Status','visibleName':'Status','preferredWidth':55,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(STATUS.getDescription()) + "'},")
            .append("{'id':" + REQUEST_LENGTH + ",'name':'RequestLength','defaultVisibleName':'Request Length','visibleName':'Request Length','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(REQUEST_LENGTH.getDescription()) + "'},")
            .append("{'id':" + RESPONSE_LENGTH + ",'name':'ResponseLength','defaultVisibleName':'Response Length','visibleName':'Response Length','preferredWidth':125,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(RESPONSE_LENGTH.getDescription()) + "'},")
            .append("{'id':" + INFERRED_TYPE + ",'name':'InferredType','defaultVisibleName':'Inferred Type','visibleName':'Inferred Type','preferredWidth':100,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(INFERRED_TYPE.getDescription()) + "'},")
            .append("{'id':" + PARAMETER_COUNT + ",'name':'ParameterCount','defaultVisibleName':'Parameter Count','visibleName':'Parameter Count','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(PARAMETER_COUNT.getDescription()) + "'},")
            .append("{'id':" + PARAMETERS + ",'name':'Parameters','defaultVisibleName':'Parameters','visibleName':'Parameters','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(PARAMETERS.getDescription()) + "'},")
            .append("{'id':" + REFLECTED_PARAMS + ",'name':'ReflectedParams','defaultVisibleName':'Reflected Params','visibleName':'Reflected Params','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(REFLECTED_PARAMS.getDescription()) + "'},")
            .append("{'id':" + REFLECTION_COUNT + ",'name':'ReflectionCount','defaultVisibleName':'Reflection Count','visibleName':'Reflection Count','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(REFLECTION_COUNT.getDescription()) + "'},")
            .append("{'id':" + MIME_TYPE + ",'name':'MimeType','defaultVisibleName':'MIME type','visibleName':'MIME type','preferredWidth':100,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(MIME_TYPE.getDescription()) + "'},")
            .append("{'id':" + NEW_COOKIES + ",'name':'NewCookies','defaultVisibleName':'New Cookies','visibleName':'New Cookies','preferredWidth':125,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(NEW_COOKIES.getDescription()) + "'},")
            .append("{'id':" + REQUEST_TIME + ",'name':'RequestTime','defaultVisibleName':'Request Time','visibleName':'Request Time','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(REQUEST_TIME.getDescription()) + "'},")
            .append("{'id':" + RESPONSE_TIME + ",'name':'ResponseTime','defaultVisibleName':'Response Time','visibleName':'Response Time','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(RESPONSE_TIME.getDescription()) + "'},")
            .append("{'id':" + RTT + ",'name':'RTT','defaultVisibleName':'RTT (ms)','visibleName':'RTT (ms)','preferredWidth':100,'readonly':true,'order':" + colOrder++ + ",'visible':true,'description':'" + StringEscapeUtils.escapeJson(RTT.getDescription()) + "'},")
            .append("{'id':" + RESPONSE_CONTENT_TYPE + ",'name':'ResponseContentType','defaultVisibleName':'Response Content-Type','visibleName':'Response Content-Type','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(RESPONSE_CONTENT_TYPE.getDescription()) + "'},")
            .append("{'id':" + HASGETPARAM + ",'name':'HasQueryStringParam','defaultVisibleName':'QueryString?','visibleName':'QueryString?','preferredWidth':50,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(HASGETPARAM.getDescription()) + "'},")
            .append("{'id':" + HASPOSTPARAM + ",'name':'HasBodyParam','defaultVisibleName':'Body Params?','visibleName':'Body Params?','preferredWidth':50,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(HASPOSTPARAM.getDescription()) + "'},")
            .append("{'id':" + HASCOOKIEPARAM + ",'name':'HasCookieParam','defaultVisibleName':'Sent Cookie?','visibleName':'Sent Cookie?','preferredWidth':50,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(HASCOOKIEPARAM.getDescription()) + "'},")
            .append("{'id':" + SENTCOOKIES + ",'name':'SentCookies','defaultVisibleName':'Sent Cookies','visibleName':'Sent Cookies','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(SENTCOOKIES.getDescription()) + "'},")
            .append("{'id':" + USES_COOKIE_JAR + ",'name':'UsesCookieJar','defaultVisibleName':'Contains cookie jar?','visibleName':'Contains cookie jar?','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(USES_COOKIE_JAR.getDescription()) + "'},")
            .append("{'id':" + REQUEST_CONTENT_TYPE + ",'name':'RequestContentType','defaultVisibleName':'Request Content Type','visibleName':'Request Type','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(REQUEST_CONTENT_TYPE.getDescription()) + "'},")
            .append("{'id':" + REFERRER + ",'name':'Referrer','defaultVisibleName':'Referrer','visibleName':'Referrer','preferredWidth':250,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(REFERRER.getDescription()) + "'},")
            .append("{'id':" + HAS_SET_COOKIES + ",'name':'HasSetCookies','defaultVisibleName':'Set-Cookie?','visibleName':'Set-Cookie?','preferredWidth':50,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(HAS_SET_COOKIES.getDescription()) + "'},")
            .append("{'id':" + REQUEST_BODY + ",'name':'Request','defaultVisibleName':'Request Body','visibleName':'Request Body','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(REQUEST_BODY.getDescription()) + "'},")
            .append("{'id':" + REQUEST_HEADERS + ",'name':'RequestHeaders','defaultVisibleName':'Request Headers','visibleName':'Request Headers','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(REQUEST_HEADERS.getDescription()) + "'},")
            .append("{'id':" + RESPONSE_BODY + ",'name':'Response','defaultVisibleName':'Response Body','visibleName':'Response Body','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(RESPONSE_BODY.getDescription()) + "'},")
            .append("{'id':" + RESPONSE_HEADERS + ",'name':'ResponseHeaders','defaultVisibleName':'Response Headers','visibleName':'Response Headers','preferredWidth':150,'readonly':true,'order':" + colOrder++ + ",'visible':false,'description':'" + StringEscapeUtils.escapeJson(RESPONSE_HEADERS.getDescription()) + "'}")
            .append("]").toString();

    public static final Pattern UUID_COMMENT_PATTERN = Pattern.compile("\\$LPP:(\\d\\d):(.*?)\\$");
}
