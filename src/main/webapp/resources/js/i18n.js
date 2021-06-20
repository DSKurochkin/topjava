const mes = ["common.deleted", "common.saved", "common.enabled", "common.disabled", "common.errorStatus", "common.confirm"];
const i18n = [];
mes.forEach(el => i18n[el] = "<spring:message code=" + "'" + el + "' javaScriptEscape='true' />");

