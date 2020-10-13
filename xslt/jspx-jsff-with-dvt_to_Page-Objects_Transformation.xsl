<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:af="http://xmlns.oracle.com/adf/faces/rich"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:trh="http://myfaces.apache.org/trinidad/html"
    xmlns:c="http://java.sun.com/jsp/jstl/core" 
	xmlns:dvt="http://xmlns.oracle.com/dss/adf/faces"
    xmlns:wf="http://xmlns.oracle.com/bpel/workflow/workflow-taglib.tld"
    xmlns:wlc="http://xmlns.oracle.com/bpel/workflow/worklist"
	>
	
<xsl:output method="text"/>
<xsl:strip-space elements="*" />

<xsl:variable name="vLower" select="'abcdefghijklmnopqrstuvwxyz'"/>
<xsl:variable name="vUpper" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>

<xsl:template match="/">
/*
 * XSLT Generated 
 * Код моделирующий Page Object для Adf Faces Tag, которые предполагается использовать в автотестах. Список редактируется по мере необходимости в xsl:template match=
 *	константа со значением AbsoluteId компонента, который используется для локации компонента на странице методом  findAdfComponent() библиотеки SeleniumTools
 *	find-метод возвращающий экземпляр объекта на странице.
 * См.
 * https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/oracle/adf/view/js/base/AdfPage.html#findComponentByAbsoluteId_String_
 * https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/oracle/adf/view/js/component/AdfUIComponent.html#findComponent_String__Boolean_
*/
<!--
Here set the component type starting processing from.
For example, 
1) set select = "//af:popup" in order to process child components of every popup not regarding to it hierarchy
2) set select = "node()" in order to process from the top of document
3) set select = "//af:table[@id='t8']" in order to process child components of particular table with id=t8
  -->

/*
 * /form | f:facet name="top"/
 * Методы обращения к компонентам секции Приветствие 
*/  
<xsl:apply-templates select = "//af:panelGridLayout[@id='pgl24']"/>

/*
 * Методы обращения к компонентам popup Create Task
*/  
<xsl:apply-templates select = "//af:popup[@id='pPlanTask']"/>

/*
 * Методы обращения к компонентам popup Close Task
*/  
<xsl:apply-templates select = "//af:popup[@id='p3']"/>

/*
 * Методы обращения к компонентам popup Поиска клиента для отчета по ДЗ
*/  
<xsl:apply-templates select = "//af:popup[@id='p4']"/>

/*
 * Методы обращения к компонентам popup Upload File
*/  
<xsl:apply-templates select = "//af:popup[@id='p5']"/>

/*
 * Методы обращения к компонентам popup Transaction Add (Обещанный платеж)
*/  
<xsl:apply-templates select = "//af:popup[@id='p6']"/>

/*
 * Методы обращения к компонентам popup Ожидания загрузки
*/  
<xsl:apply-templates select = "//af:popup[@id='busyStatePopup']"/>

/*
 * /form | f:facet name="center" | panelSplitter | f:facet name="first" | af:panelAccordion id="pa1"/
 * Методы обращения к компонентам секции аккордеона Фильтр подчиненных менеджеров
*/  
<xsl:apply-templates select = "//af:showDetailItem[@id='sdi1']"/>

/*
 * Методы обращения к компонентам секции аккордеона Фильтр задач
*/  
<xsl:apply-templates select = "//af:showDetailItem[@id='sdi2']"/>

/*
 * Методы обращения к компонентам секции аккордеона Меню отчетов по задачам
*/  
<xsl:apply-templates select = "//af:showDetailItem[@id='sdi3']"/>

/*
 * Методы обращения к компонентам секции аккордеона Меню отчетов по ДЗ
*/  
<xsl:apply-templates select = "//af:showDetailItem[@id='sdi4']"/>

/*
 * /form | f:facet name="center" | panelSplitter | f:facet name="second" | f:facet name="top" | f:facet name="start" /
 * Методы обращения к компонентам секции Меню Ганта
*/  
<xsl:apply-templates select = "//af:panelGroupLayout[@id='pgl35']"/>

/*
 * /form | f:facet name="center" | panelSplitter | f:facet name="second" | f:facet name="top" | f:facet name="end" /
 * Методы обращения к компонентам таблицы KPI
*/  
<xsl:apply-templates select = "//af:panelGridLayout[@id='pgl26']"/>

/*
 * /form | f:facet name="center" | panelSplitter | f:facet name="second" | f:facet name="center" | panelSplitter | f:facet name="first" /
 * Методы обращения к компонентам диаграммы Ганта с задачами
*/  
<xsl:apply-templates select = "//dvt:schedulingGantt[@id='sg1']"/>

/*
 * /form | f:facet name="center" | panelSplitter | f:facet name="second" | f:facet name="center" | panelSplitter | f:facet name="second" /
 * Методы обращения к компонентам секции Информация о клиенте
*/  
<xsl:apply-templates select = "//af:panelHeader[@id='ph1']"/>

/*
 * Методы обращения к компонентам секции Информация о задаче
*/  
<xsl:apply-templates select = "//af:panelHeader[@id='ph2']"/>

</xsl:template>

<!--
This template is used to generate part of Page Objects Model representing Adf Components inside Naming Containers.

This template generates 
a) the statement for AbsoluteId of Adf Component only: final static String _id ="NC1id:NC2:ComponentId value"
b) the statement for general implementation of find-Method. Implementation of find-Method bases on calling com.redheap.selenium.component.AdfComponent.findAdfComponent(_id).
 -->

<xsl:template match=   "af:button	|
						af:column	|
						af:commandButton	|
						af:commandLink	|
						af:commandMenuItem	|
						af:commandNavigationItem	|
						af:commandToolbarButton	|
						af:dialog	|
						af:inlineFrame	|
						af:inputComboboxListOfValues	|
						af:inputDate	|
						af:inputFile	|
						af:inputListOfValues	|
						af:inputNumberSpinbox	|
						af:inputText	|
						af:link	|
						af:listView |
						af:menu	|
						af:outputFormatted	|
						af:outputText	|
						af:popup	|
						af:panelGridLayout |
						af:selectBooleanCheckbox	|
						af:selectBooleanRadio	|
						af:selectManyChoice	|
						af:selectOneChoice	|
						af:selectOneListbox	|
						af:selectOneRadio	|
						af:showDetailItem |
						af:table |
						af:toolbar |
						dvt:schedulingGantt |
						dvt:timeAxis">

<xsl:variable name="vAdfTagName" select="local-name()"/>
<xsl:variable name="vAdfTagNameCamel" select="concat(translate(substring($vAdfTagName,1,1), $vLower, $vUpper), substring($vAdfTagName,2))"/>
<xsl:variable name="vAdfComponentClassName" select="concat('Adf',$vAdfTagNameCamel)"/>

<xsl:variable name="vAdfElementName" >
   <xsl:choose>
     <xsl:when test="substring-before(substring-after(@value,'#{bindings.'),'.') != '' ">
        <xsl:value-of select="substring-before(substring-after(@value,'#{bindings.'),'.')"/>
     </xsl:when>
     <xsl:otherwise>
       <xsl:value-of select="concat('_',@id)"/>
     </xsl:otherwise>
   </xsl:choose>
</xsl:variable> 

<xsl:variable name="vAdfElementNameLow" select ="concat(translate(substring($vAdfElementName,1,1), $vUpper, $vLower), substring($vAdfElementName,2))"/>
<xsl:variable name="vAdfElementNameUpper" select ="concat(translate(substring($vAdfElementName,1,1), $vLower, $vUpper), substring($vAdfElementName,2))"/>

<xsl:variable name="vIdStringName" select ="concat($vAdfElementNameLow,$vAdfTagNameCamel)"/>
<xsl:variable name="vAdfFindMethodName" select ="concat('find',$vAdfElementNameUpper)"/>
static final String <xsl:value-of select = "$vIdStringName" /> = "<xsl:apply-templates 	select="ancestor::*[self::af:breadCrumbs |
																										   self::af:calendar |
																										   self::af:carousel |
                                                                                                           self::af:declarativeComponent |
																										   self::af:iterator |
																										   self::af:listView |
																										   self::af:navigationPane | 
																										   self::af:panelCollection | 
																										   self::af:query | 
																										   self::af:quickQuery | 
																										   self::af:region | 
																										   self::af:pageTemplate | 
																										   self::af:subform | 
																										   self::af:table | 
																										   self::af:train |
																										   self::af:trainButtonBar |
                                                                                                           self::af:treeTable | 
                                                                                                           self::af:tree |
																										   self::af:subform |
																										   self::dvt:schedulingGantt]" 				
																						mode="collectAbsoluteId"/><xsl:value-of select="@id"/>";

public <xsl:value-of select = "$vAdfComponentClassName"/> <xsl:text> </xsl:text> <xsl:value-of select = "$vAdfFindMethodName"/> () {
	return findAdfComponent(<xsl:value-of select = "$vIdStringName" />);
}
   <xsl:choose>
     <xsl:when test="name() = 'af:listView' or 
					 name() = 'af:table' or
					 name() = 'af:carousel' or
					 name() = 'af:column'">
		<!--For af:table, af:listView, af:carousel, in other words Direct Known Subclasses of ADFUIIterator Applying specific template which generates get-Methods-->
		<!--Aslo for af:colums inside SchedulingGannt we apply specific template which generates get-Methods for the stamped inside columns components-->
/*
 * Методы для локации stamped-компонентов в колонках SchedulingGantt
*/
		<xsl:apply-templates select = "node()" mode ="stampedComponents"/>
     </xsl:when>
     <xsl:otherwise>
		<!--Applying general template which generate find-Methods-->
       <xsl:apply-templates select = "node()"/>
     </xsl:otherwise>
   </xsl:choose>
</xsl:template>

<!--
This template is used to generate part of Page Objects Model representing Adf Components stamped in the rows of af:table, af:listView, af:carousel, in other words Direct Known Subclasses of ADFUIIterator. See https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/toc.htm

This is the specific template of one above.
This one generates the statement for ComponentId of Adf Component only: final static String _id ="id-attribute value"
The find-Method is not generated instead get-Methods are generated for id-string-properties.
 -->

<xsl:template mode ="stampedComponents" 
			  match=   "af:button	|
						af:commandButton	|
						af:commandLink	|
						af:commandMenuItem	|
						af:commandNavigationItem	|
						af:commandToolbarButton	|
						af:inputComboboxListOfValues	|
						af:inputDate	|
						af:inputFile	|
						af:inputListOfValues	|
						af:inputNumberSpinbox	|
						af:inputText	|
						af:link	|
						af:menu	|
						af:outputFormatted	|
						af:outputText	|
						af:selectBooleanCheckbox	|
						af:selectBooleanRadio	|
						af:selectManyChoice	|
						af:selectOneChoice	|
						af:selectOneListbox	|
						af:selectOneRadio	|
						af:showDetailItem">

<xsl:variable name="vAdfTagName" select="local-name()"/>
<xsl:variable name="vAdfTagNameCamel" select="concat(translate(substring($vAdfTagName,1,1), $vLower, $vUpper), substring($vAdfTagName,2))"/>

<xsl:variable name="vAdfElementName" >
   <xsl:choose>
     <xsl:when test="substring-before(substring-after(@value,'#{bindings.'),'.') != '' ">
        <xsl:value-of select="substring-before(substring-after(@value,'#{bindings.'),'.')"/>
     </xsl:when>
     <xsl:otherwise>
       <xsl:value-of select="concat('_',@id)"/>
     </xsl:otherwise>
   </xsl:choose>
</xsl:variable> 

<xsl:variable name="vAdfElementNameLow" select ="concat(translate(substring($vAdfElementName,1,1), $vUpper, $vLower), substring($vAdfElementName,2))"/>

<xsl:variable name="vIdStringName" select ="concat($vAdfElementNameLow,$vAdfTagNameCamel,'Id')"/>
static final String <xsl:value-of select = "$vIdStringName" /> = "<xsl:value-of select="@id"/>";
public static final String get<xsl:value-of select = "$vIdStringName" />() {
    return <xsl:value-of select = "$vIdStringName" />;
}
<xsl:apply-templates select = "node()" mode ="stampedComponents"/>
</xsl:template>

<!--This Template for collecting id-attribute values from selected ancestor's - naming container's of processed node-->
<!--
Note, that according to XSL specification <xsl:template match= /> could accept only child and attributes axes elements in pattern.
That is why all needed naming container's should be selected in <xsl:apply-templates> statement and here in template itself match="*" is used.
--> 
<xsl:template 	match="*"				mode="collectAbsoluteId">
	<xsl:value-of select="@id"/>
	<xsl:text>:</xsl:text>
</xsl:template>

</xsl:stylesheet>