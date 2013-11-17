/**
 *  Copyright 2011 Marco Berri - marcoberri@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 **/
package it.marcoberri.mbfasturl.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Marco Berri marcoberri@gmail.com
 */
public class HttpUtil {

    /**
     *
     * @param url
     * @param target
     */
    public static synchronized void downloadData(String url, String target) {

        try {
            org.apache.commons.io.FileUtils.copyURLToFile(new URL(url), new File(target));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }


    }
}
