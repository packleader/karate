package com.intuit.karate.template;

import com.intuit.karate.graal.JsEngine;
import com.intuit.karate.resource.ResourceResolver;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pthomas3
 */
class TemplateTest {
    
    static final Logger logger = LoggerFactory.getLogger(TemplateTest.class);
    
    @Test
    void testHtmlString() {
        JsEngine je = JsEngine.global();
        je.put("message", "hello world");
        KarateTemplateEngine engine = TemplateUtils.forStrings(je, new ResourceResolver("classpath:com/intuit/karate/template"));
        String rendered = engine.process("<div><div th:text=\"message\"></div><div th:replace=\"root:temp.html\"></div></div>");
        assertEquals("<div><div>hello world</div><div>temp</div></div>", rendered);
    }
    
    @Test
    void testHtmlFile() {
        JsEngine je = JsEngine.local();
        KarateTemplateEngine engine = TemplateUtils.forResourceRoot(je, "classpath:com/intuit/karate/template");
        String rendered = engine.process("main.html");
        // logger.debug("rendered: {}", rendered);
        assertTrue(rendered.contains("<div id=\"before_one\"><span>js_one</span></div>"));
        assertTrue(rendered.contains("<div id=\"called_one\">called_one</div>"));
        assertTrue(rendered.contains("<div id=\"after_one\"><span>js_one</span></div>"));
    }    
    
}
