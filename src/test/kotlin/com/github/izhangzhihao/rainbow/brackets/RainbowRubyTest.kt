package com.github.izhangzhihao.rainbow.brackets

import com.intellij.psi.PsiDocumentManager
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import io.kotlintest.matchers.shouldBe
import org.jetbrains.plugins.ruby.ruby.lang.RubyFileType

class RainbowRubyTest : LightCodeInsightFixtureTestCase() {
    fun testRainbowForIssue53Part0() {
        val code =
                """
class Test
  a = (1 + 2 + (3))
end
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting.filter { brackets.contains(it.text.toChar()) }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                roundLevel(1),
                                roundLevel(1),
                                roundLevel(0)
                        )
                )
    }

    fun testRainbowForIssue53Part1() {
        val code =
                """
foobar(p1: "", p2: false, p3: 1)
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting.filter { brackets.contains(it.text.toChar()) }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                roundLevel(0)
                        )
                )
    }

    fun testRainbowForIssue53Part2() {
        val code =
                """
def foo
  some_function(true, { :before => {a: 1},
                        :after => {b: 2} })
  call_block { do_something }
end
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting//.filter { brackets.contains(it.text.toChar()) }
                .filterNot { it.forcedTextAttributes == null }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                roundLevel(0),
                                squigglyLevel(0),
                                squigglyLevel(1),
                                squigglyLevel(1),
                                squigglyLevel(1),
                                squigglyLevel(1),
                                squigglyLevel(0),
                                roundLevel(0),
                                squigglyLevel(0),
                                squigglyLevel(0),
                                roundLevel(0)
                        )
                )
    }

    fun testRainbowForIssue53Part3() {
        val code =
                """
def bar
  case @var
    when 1
      print [1..42]
    else
      print [1...42]
  end
end
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting//.filter { brackets.contains(it.text.toChar()) }
                .filterNot { it.forcedTextAttributes == null }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                roundLevel(0),
                                squareLevel(0),
                                squareLevel(0),
                                squareLevel(0),
                                squareLevel(0),
                                roundLevel(0),
                                roundLevel(0)
                        )
                )
    }

    fun testRainbowForIssue53Part4() {
        val code =
                """
if true
  A = [1, [2, [3] ] ]
end
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting//.filter { brackets.contains(it.text.toChar()) }
                .filterNot { it.forcedTextAttributes == null }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                squareLevel(0),
                                squareLevel(1),
                                squareLevel(2),
                                squareLevel(2),
                                squareLevel(1),
                                squareLevel(0),
                                roundLevel(0)
                        )
                )
    }
}