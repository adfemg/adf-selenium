<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:af="http://xmlns.oracle.com/adf/faces/rich"
	xmlns:afh="http://myfaces.apache.org/trinidad/html"
	xmlns:wlc="http://xmlns.oracle.com/bpel/workflow/worklist"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
	
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
 * Метод для локации региона из центральной секции страницы f:facet name="center"
*/
<xsl:apply-templates select = "//af:region[@id='r1']"/>
  
/*
 * Методы для локации объектов верхней секции страницы f:facet name="top"
*/
<xsl:apply-templates select = "//af:panelGridLayout[@id='pgl1']"/>

/*
 * Методы для локации объектов размещенных в нижней секции страницы f:facet name="bottom"
*/
<xsl:apply-templates select = "//af:panelGroupLayout[@id='pgl3']"/>

</xsl:template>

<!--
This template is used to generate part of Page Objects Model representing Adf Components inside Naming Containers.

This template generates 
a) the statement for AbsoluteId of Adf Component only: final static String _id ="NC1id:NC2:ComponentId value"
b) the statement for general implementation of find-Method. Implementation of find-Method bases on calling com.redheap.selenium.component.AdfComponent.findAdfComponent(_id).
 -->
  <xsl:template match="af:button	|
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
						af:panelWindow |
						af:query |
						af:region |
                        af:selectBooleanCheckbox	|
                        af:selectBooleanRadio	|
                        af:selectManyChoice	|
                        af:selectOneChoice	|
                        af:selectOneListbox	|
                        af:selectOneRadio	|
                        af:showDetailItem |
						af:table">

<xsl:variable name="vAdfTagName" select="local-name()"/>
<xsl:variable name="vAdfTagNameCamel"
              select="concat(translate(substring($vAdfTagName,1,1), $vLower, $vUpper), substring($vAdfTagName,2))"/>
<xsl:variable name="vAdfComponentClassName" select="concat('Adf',$vAdfTagNameCamel)"/>

<!--Try extract form #{bindings string text for the element name to use later in method's name -->
<xsl:variable name="vAdfElementName">
   <xsl:choose>
        <xsl:when test="substring-before(substring-after(@value,'#{bindings.'),'.') != '' ">
        <xsl:value-of select="substring-before(substring-after(@value,'#{bindings.'),'.')"/>
     </xsl:when>
        <xsl:otherwise>
       <xsl:value-of select="concat('_',@id)"/>
     </xsl:otherwise>
      </xsl:choose>
</xsl:variable> 

<xsl:variable name="vAdfElementNameLow"
              select="concat(translate(substring($vAdfElementName,1,1), $vUpper, $vLower), substring($vAdfElementName,2))"/>
<xsl:variable name="vAdfElementNameUpper"
              select="concat(translate(substring($vAdfElementName,1,1), $vLower, $vUpper), substring($vAdfElementName,2))"/>

<!--If processed node has ancestor-popup then use popup's id as part of java variables and methods name to make it unique   -->
<xsl:variable name="vParentPopupId">
   <xsl:choose>
        <xsl:when test="ancestor::af:popup">
        <xsl:value-of select="concat('_On_',ancestor::af:popup[1]/@id)"/>
     </xsl:when>
        <xsl:otherwise>
       <xsl:value-of select="''"/>
     </xsl:otherwise>
      </xsl:choose>
</xsl:variable> 

<xsl:variable name="vIdStringName" select="concat($vAdfElementNameLow,$vAdfTagNameCamel,$vParentPopupId)"/>
<xsl:variable name="vAdfFindMethodName" select="concat('find',$vAdfElementNameUpper,$vParentPopupId)"/>
<xsl:variable name="vAbsoluteId">
	<xsl:apply-templates select="ancestor::*[self::af:breadCrumbs |
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
											 self::af:subform]"
						 mode="collectAbsoluteId"/><xsl:value-of select="@id"/>
</xsl:variable>

<!--If processed node is popup then try extract text for the comment about that popup form @title or @binding of any child of popup   -->
<xsl:choose>
 <xsl:when test="name() = 'af:popup'">
/*
 * Методы для локации объектов popup '<xsl:choose>
										<xsl:when test="descendant::*[@title]">
											<xsl:value-of select="*[@title]/@title"/>'
										</xsl:when>
										<xsl:when test="descendant::*[@text]">
											<xsl:value-of select="descendant::*[@text][1]/@text"/>'
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="*[@binding]/@binding"/>'
										</xsl:otherwise>
									  </xsl:choose>
*/
 </xsl:when>
</xsl:choose>
static final String <xsl:value-of select="$vIdStringName"/> = "<xsl:value-of select="$vAbsoluteId"/>";

public <xsl:value-of select="$vAdfComponentClassName"/> <xsl:text> </xsl:text> <xsl:value-of select="$vAdfFindMethodName"/> () {
	return findAdfComponent(<xsl:value-of select="$vIdStringName"/>);
}
	<xsl:choose>
		<!--Special transforming for af:region and other ADF Faces tags that locate Fragments -->
		<xsl:when test="name() = 'af:region'">
public 'Insert here appropriate fragment's class name' get<xsl:value-of select="$vIdStringName"/>Content () {
    //Add here code for fragment's initialization action on you page. Like: findOrderListTab().click();
    return new 'Insert here call of appropriate fragment constructor'(<xsl:value-of select="$vAdfFindMethodName"/>());
}
		</xsl:when>
		<!--Then transforming for all others ADF Faces tags-->
		<xsl:otherwise>
			<xsl:choose>
				<!--Special transforming for af:table, af:listView, af:carousel -->
				<xsl:when test="name() = 'af:listView' or 
								name() = 'af:table' or
								name() = 'af:carousel'">
				<!--For all non-column-childes of af:table, af:listView, af:carousel, in other words Direct Known Subclasses of ADFUIIterator applying specific template which generates get-Methods-->
/*
 * Метод для локации stamped-объектов таблицы
*/
				<xsl:apply-templates select="node()" mode="stampedComponents"/>
				<!--For all column-childes of af:table, af:listView, af:carousel, applying general template which generates and generate find-Methods. And see the next comment below -->
/*
 * Метод для локации объектов колонок таблицы
*/
				<xsl:apply-templates select="af:column"/>
				</xsl:when>
				<!--For childes of columns do nothing, because childes of columns are actually childes of af:table, af:listView, af:carousel-->
				<xsl:when test="name() = 'af:column'">
				<!--Do nothing here and stop processing the on the column itself-->
				</xsl:when>
				<!--Then transforming for the rest of ADF Faces tags-->
				<xsl:otherwise>
				<!--Applying general template which generate find-Methods-->
					<xsl:apply-templates select="node()"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
This template is used to generate part of Page Objects Model representing Adf Components stamped in the rows of af:table, af:listView, af:carousel, in other words Direct Known Subclasses of ADFUIIterator. See https://docs.oracle.com/middleware/12213/adf/api-reference-javascript-faces/toc.htm
This is the specific template of one above.
This one generates the statement for ComponentId of Adf Component only: final static String _id ="id-attribute value"
The find-Method is not generated instead get-Methods are generated for id-string-properties.
Note: <af:column> is not stamped inside the table, so usually it should not be included in match attribute below.
	  But for taskTable we need work with table as WebElement and will use for columns also ComponentId's and get-Methods.
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
<xsl:template 	match="*"   mode="collectAbsoluteId">
	<xsl:value-of select="@id"/>
	<xsl:text>:</xsl:text>
</xsl:template>

</xsl:stylesheet>