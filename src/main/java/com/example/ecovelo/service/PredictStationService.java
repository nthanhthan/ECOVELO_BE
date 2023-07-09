package com.example.ecovelo.service;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.python.core.PyObject;

@Service
@RequiredArgsConstructor
public class PredictStationService {
    PythonInterpreter interpreter = new PythonInterpreter();
    void loadLib() {
    	   interpreter.exec("import sys");
           interpreter.exec("print(sys.version)");
           interpreter.exec("print(sys.exec_prefix)");
           String jythonPath = interpreter.eval("sys.exec_prefix").toString();
           Path joblibPath = Paths.get(jythonPath, "Lib", "site-packages", "joblib.py");
           if (Files.exists(joblibPath)) {
               System.out.println("joblib is installed.");
           } else {
               System.out.println("joblib is not installed.");
           }
//        try {
//            Process p = Runtime.getRuntime().exec("jython -m ensurepip");
//            p.waitFor();
//
//            p = Runtime.getRuntime().exec("jython -m pip install joblib");
//            p.waitFor();
//            interpreter.exec("import joblib");
//            interpreter.exec("model = joblib.load('/Users/nthanhthan/Downloads/kmeans_model.joblib')");
//            interpreter.exec("labels = model.labels_");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            interpreter.close();
//        }
    }
}
