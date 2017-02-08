<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${param.idioma}" scope="session"/>
<c:set var="idioma" value="${param.idioma}" scope="session"/>
<c:redirect url="/"/>