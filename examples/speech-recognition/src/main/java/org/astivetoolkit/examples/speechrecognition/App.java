/* 
 * Copyright (C) 2010-2016 by Fonoster Inc (http://fonoster.com)
 * http://astivetoolkit.org
 *
 * This file is part of Astive Toolkit(ATK)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.astivetoolkit.examples.speechrecognition;

import static java.lang.System.out;
import org.astivetoolkit.agi.AgiException;
import org.astivetoolkit.agi.SpeechRecognitionResult;
import org.astivetoolkit.astivlet.Astivlet;
import org.astivetoolkit.astivlet.AstivletRequest;
import org.astivetoolkit.astivlet.AstivletResponse;

/**
 * Speech Recognition example
 * 
 * @since 1.0
 */
public class App extends Astivlet {

    @Override
    public void service(AstivletRequest request, AstivletResponse response) {
        try {
            response.answer();
            
            response.speechCreate();
            response.speechLoadGrammar("digits", "");
            response.speechActivateGrammar("digits");
            SpeechRecognitionResult srr  = response.speechRecognize("hello-world", 12);
            
            response.speechDeactivateGrammar("digits");
            
            response.hangup();
        } catch (AgiException ex) {
            out.print(ex.getMessage());
        }
    }
}
