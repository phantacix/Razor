/**
 * Copyright (c) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.fedepot.mvc.renderer;

import com.fedepot.exception.RazorException;
import com.fedepot.mvc.http.ContentType;
import com.fedepot.mvc.http.Request;
import com.fedepot.mvc.http.Response;
import com.fedepot.mvc.template.TemplateEngineFactory;

import java.util.HashMap;
import java.util.Map;

import static com.fedepot.mvc.http.HttpHeaderNames.*;

/**
 * Renderer using template
 *
 * @author Touchumind
 * @since 0.0.1
 */
public class TemplateRenderer extends Renderer {

    /**
     * Path relative to classpath
     */
    private String templatePath;

    private Map<String, Object> model;

    public TemplateRenderer(String path) {

        this.templatePath = path;
        this.model = new HashMap<>();

    }

    public TemplateRenderer(String path, Map<String, Object> model) {

        this.templatePath = path;
        this.model = model;
    }

    public TemplateRenderer(String path, String dataKey, Object dataValue) {

        this.templatePath = path;
        this.model = new HashMap<>();
        this.model.put(dataKey, dataValue);
    }

    @Override
    public void render(Request request, Response response) throws RazorException {

        try {

            model.put("VIEWBAG", getViewBag());
            String view = TemplateEngineFactory.getEngine().render(templatePath, model);

            if (response.get(CONTENT_TYPE) == null) {

                ContentType contentType = getContentType() != null ? getContentType() : ContentType.TEXT;
                response.header(CONTENT_TYPE, contentType.getMimeTypeWithCharset());
            }

            response.end(view);

        } catch (Exception e) {

            throw new RazorException(e);
        }
    }
}
