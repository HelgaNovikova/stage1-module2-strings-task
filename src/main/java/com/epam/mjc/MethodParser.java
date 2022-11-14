package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */

    public MethodSignature parseFunction(String signatureString) {
        StringTokenizer st = new StringTokenizer(signatureString, "()");
        List<String> parts = new ArrayList<>();
        while (st.hasMoreTokens())
            parts.add(st.nextToken());
        StringTokenizer nameAndModifier = new StringTokenizer(parts.get(0), " ");
        List<String> nameAndModifierList = new ArrayList<>();
        while (nameAndModifier.hasMoreTokens())
            nameAndModifierList.add(nameAndModifier.nextToken());
        String accessModifier = "";
        String returnType = "";
        String methodName = "";
        if (nameAndModifierList.size() == 2) {
            accessModifier = "";
            returnType = nameAndModifierList.get(0);
            methodName = nameAndModifierList.get(1);
        } else if (nameAndModifierList.size() == 3) {
            accessModifier = nameAndModifierList.get(0);
            returnType = nameAndModifierList.get(1);
            methodName = nameAndModifierList.get(2);
        }
        List<MethodSignature.Argument> arguments = new ArrayList<>();
        if (parts.size() > 1) {
            StringTokenizer parameters = new StringTokenizer(parts.get(1), ",");
            List<String> paramList = new ArrayList<>();
            while (parameters.hasMoreTokens())
                paramList.add(parameters.nextToken());
            for (String item : paramList) {
                int space = item.trim().indexOf(" ");
                arguments.add(new MethodSignature.Argument(item.trim().substring(0, space), item.trim().substring(space + 1)));
            }
        }
        MethodSignature ms = new MethodSignature(methodName, arguments);
        if (!accessModifier.equals("")) {
            ms.setAccessModifier(accessModifier);
        }

        ms.setReturnType(returnType);
        return ms;

    }
}
