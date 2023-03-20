/*
 * Copyright 2018, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.SimpleCalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * SimpleCalc is the initial version of SimpleCalcTest.  It has
 * a number of intentional oversights for the student to debug/fix,
 * including input validation (no input, bad number format, div by zero)
 *
 * In addition there is only one (simple) unit test in this app.
 * All the input validation and the unit tests are added as part of the lessons.
 *
 */
public class MainActivity extends Activity {

    private static final String TAG = "CalculatorActivity";

    private Calculator mCalculator;

    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;

    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the calculator class and all the views
        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mOperandOneEditText = findViewById(R.id.operand_one_edit_text);
        mOperandTwoEditText = findViewById(R.id.operand_two_edit_text);
    }

    /**
     * OnClick method called when the add Button is pressed.
     */
    public void onAdd(View view) {
        compute(Calculator.Operator.ADD);
    }

    /**
     * OnClick method called when the subtract Button is pressed.
     */
    public void onSUB(View view) {
        compute(Calculator.Operator.SUB);
    }
    /**
     * OnClick method called when the power Button is pressed.
     */
    public void onPOW(View view) {
        compute(Calculator.Operator.POW);
    }

    /**
     * OnClick method called when the divide Button is pressed.
     */
    public void onDiv(View view) {
        compute(Calculator.Operator.DIV);
    }

    /**
     * OnClick method called when the multiply Button is pressed.
     */
    public void onMul(View view) {
        compute(Calculator.Operator.MUL);
    }

    private void compute(Calculator.Operator operator) {
        double operandOne;
        double operandTwo;
        try {
            if(mOperandOneEditText.length()==0||mOperandTwoEditText.length()==0)
            {
                throw new Exception("EmptyOperand");
            }
            else if(operator==Calculator.Operator.POW)
            {
                if(mOperandTwoEditText.getText().toString().contains("."))
                {
                    throw new Exception("IntegerOperandMissing");
                }
            }
            operandOne = getOperand(mOperandOneEditText);
            operandTwo = getOperand(mOperandTwoEditText);

        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            mResultTextView.setText(getString(R.string.computationError));
            return;
        } catch (Exception e) {
            if(e.getMessage()=="EmptyOperand") {
                Log.e(TAG, "Empty Input!: ", e);
                mResultTextView.setText("Input Operand Empty!\nPlease input something for both operands!");
            }else if(e.getMessage()=="IntegerOperandMissing") {
                Log.e(TAG, "Integer Expected!: ", e);
                mResultTextView.setText(Html.fromHtml("For X<sup>Y</sup>, the Second operand must be an integer!"));
            }
            else{
                Log.e(TAG, "Unknown Error!: ", e);
            }
            return;
        }

        String result;
        switch (operator) {
            case ADD:
                result = String.valueOf(
                        mCalculator.add(operandOne, operandTwo));
                break;
            case SUB:
                result = String.valueOf(
                        mCalculator.sub(operandOne, operandTwo));
                break;
            case POW:
                result = String.valueOf(
                        mCalculator.pow(operandOne, operandTwo));
                break;

                /* in this the function was (mCalculator.sub(operandOne, operandTwo))) sub instaed
                of div but sub will substract
                both opernads instead of dividing and giving the write answer.
                 */
                case DIV:
              result = String.valueOf(
                      mCalculator.div(operandOne, operandTwo));
              break;


                /*I mentioned the correct statement below before their was a missing break after
            mCalculator.mul(operandOne, operandTwo));
            method call . without the break statement the code will show default value inspite if the
            mul case is matched but the result of variable will set computation error.
            */
            case MUL:
                result = String.valueOf(
                    mCalculator.mul(operandOne, operandTwo));
            break;
            default:
                result = getString(R.string.computationError);
            break;
        }
        mResultTextView.setText(result);
    }
    /**
     * @return the operand value entered in an EditText as double.
     */
    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    /**
     * @return the operand text which was entered in an EditText.
     */
    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }
}