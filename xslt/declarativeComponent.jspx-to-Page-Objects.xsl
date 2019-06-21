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
 * This is Page Object Model for the tested ADF Page.
 * The model includes java code for those of Adf Faces Tag which are listed xsl:template match=. You can change this list according to your needs and re-generate code for this class.
 * Generated code includes: 
 *	- String constant with ADF component AbsoluteId value. This constant then is used to locate component by corresponding findAdfComponent() method of SeleniumTools library.
 *	- find-method which returm object instance representing ADF component.
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
 * Methods to locate and interract with components in the section f:facet name="first"
*/  
<xsl:apply-templates select = "//f:facet[@name='first']"/>


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
						af:declarativeComponent |
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
						af:region |
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
     <xsl:when test="substring-before(substring-after(@value,'#{demoLOV.'),'}') != '' ">
        <xsl:value-of select="substring-before(substring-after(@value,'#{demoLOV.'),'}')"/>
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
		<!--Special transforming for af:region and other ADF Faces tags that locate Fragments -->
		<xsl:when test="name() = 'af:region'">
public 'Customize here fragment's class name'Fragment get<xsl:value-of select="$vIdStringName"/>Content () {
    //Add here code for fragment's initialization action on you page. Like: findOrderListTab().click();
    return new 'Insert here call of appropriate fragment constructor'(<xsl:value-of select="$vAdfFindMethodName"/>());
}
		</xsl:when>
		
		<xsl:when test="name() = 'af:declarativeComponent'">
public 'Customize here fragment's class name'Fragment get<xsl:value-of select="$vIdStringName"/>Content () {
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
 * Methods to locate stamped-components
*/
				<xsl:apply-templates select="node()" mode="stampedComponents"/>
				<!--For all column-childes of af:table, af:listView, af:carousel, applying general template which generates and generate find-Methods. And see the next comment below -->
/*
 * Methods to locate column-components itself
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
     <xsl:when test="substring-before(substring-after(@value,'#{demoLOV.'),'}') != '' ">
        <xsl:value-of select="substring-before(substring-after(@value,'#{demoLOV.'),'}')"/>
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