# mp2 Feedback

## Grade: 4.0

| Compilation | Timeout | Duration |
|:-----------:|:-------:|:--------:|
|Yes|No|9.37|

## Test Results
### ca.ubc.ece.cpen221.graphs.one.Task2Grader
| Test Status | Count |
| ----------- | ----- |
|skipped|0|
|failures|0|
|errors|0|
|tests|16|
### ca.ubc.ece.cpen221.graphs.one.Task3Grader
| Test Status | Count |
| ----------- | ----- |
|skipped|0|
|failures|12|
|errors|0|
|tests|28|
#### Failed Tests
1. `testDiameter[0] (java.lang.AssertionError: Incorrect diameter expected:<4> but was:<7>)`
1. `testDistance[2] (java.lang.AssertionError: Runtime exception is not appropriate for shortest path)`
1. `testDistance[3] (java.lang.AssertionError: Runtime exception is not appropriate for shortest path)`
1. `testDiameter[4] (java.lang.AssertionError: Incorrect diameter expected:<2> but was:<3>)`
1. `testDiameter[5] (java.lang.AssertionError: Runtime exception not suitable for diameter)`
1. `testDiameter[6] (java.lang.AssertionError: Incorrect diameter expected:<5> but was:<6>)`
1. `testDiameter[7] (java.lang.AssertionError: Incorrect diameter expected:<4> but was:<7>)`
1. `testDistance[9] (java.lang.AssertionError: Runtime exception is not appropriate for shortest path)`
1. `testDistance[10] (java.lang.AssertionError: Runtime exception is not appropriate for shortest path)`
1. `testDiameter[11] (java.lang.AssertionError: Incorrect diameter expected:<2> but was:<3>)`
1. `testDiameter[12] (java.lang.AssertionError: Runtime exception not suitable for diameter)`
1. `testDiameter[13] (java.lang.AssertionError: Incorrect diameter expected:<5> but was:<6>)`
### ca.ubc.ece.cpen221.graphs.one.Task4Grader
| Test Status | Count |
| ----------- | ----- |
|skipped|0|
|failures|2|
|errors|0|
|tests|12|
#### Failed Tests
1. `test_NR_INF (org.junit.ComparisonFailure: expected:<[]> but was:<[userA's post will never reach userB's feed]>)`
1. `test_NR_0 (org.junit.ComparisonFailure: expected:<[0]> but was:<[-1]>)`
### ca.ubc.ece.cpen221.graphs.one.Task1Grader
| Test Status | Count |
| ----------- | ----- |
|skipped|0|
|failures|1|
|errors|0|
|tests|16|
#### Failed Tests
1. `testNeighbours[0] (java.lang.AssertionError: Not in lexicographic order expected:<[a, d]> but was:<[d, a]>)`

## Comments
**Good implementation for Task 5**. Specs/RI/AF are good with minor issues.

RIs and AFS are good. Specs are well done minus one missing.
AdjacencyListGraph.java:8:	Avoid unused imports such as 'java.util.Comparator'

AdjacencyListGraph.java:31:	Avoid using implementation types like 'HashMap'; use the interface instead

AdjacencyListGraph.java:31:	Found non-transient, non-static member. Please mark as transient or provide accessors.

AdjacencyListGraph.java:62:	Potential violation of Law of Demeter (object not created locally)

AdjacencyListGraph.java:74:	Avoid unnecessary if..then..else statements when returning booleans

AdjacencyListGraph.java:74:	Potential violation of Law of Demeter (object not created locally)

AdjacencyMatrixGraph.java:30:	Found non-transient, non-static member. Please mark as transient or provide accessors.

AdjacencyMatrixGraph.java:30:	Private field 'graph' could be made final; it is only initialized in the declaration or constructor.

AdjacencyMatrixGraph.java:31:	Found non-transient, non-static member. Please mark as transient or provide accessors.

AdjacencyMatrixGraph.java:32:	Avoid using implementation types like 'HashMap'; use the interface instead

AdjacencyMatrixGraph.java:32:	Found non-transient, non-static member. Please mark as transient or provide accessors.

AdjacencyMatrixGraph.java:73:	Potential violation of Law of Demeter (object not created locally)

AdjacencyMatrixGraph.java:93:	Avoid unnecessary if..then..else statements when returning booleans

AdjacencyMatrixGraph.java:93:	Avoid using Literals in Conditional Statements

AdjacencyMatrixGraph.java:93:	Potential violation of Law of Demeter (method chain calls)

AdjacencyMatrixGraph.java:112:	Found 'DU'-anomaly for variable 'row' (lines '112'-'121').

AdjacencyMatrixGraph.java:116:	Avoid using Literals in Conditional Statements

Algorithms.java:9:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.

Algorithms.java:76:	Found 'DU'-anomaly for variable 'remainingList' (lines '76'-'90').

Algorithms.java:77:	Use equals() to compare object references.

Algorithms.java:107:	Found 'DD'-anomaly for variable 'allVertices' (lines '107'-'108').

Algorithms.java:107:	The initializer for variable 'allVertices' is never used (overwritten on line 108)

Algorithms.java:153:	Found 'DD'-anomaly for variable 'allVertices' (lines '153'-'154').

Algorithms.java:153:	The initializer for variable 'allVertices' is never used (overwritten on line 154)

Algorithms.java:187:	Use equals() to compare object references.

Algorithms.java:219:	Potential violation of Law of Demeter (object not created locally)

Algorithms.java:219:	Potential violation of Law of Demeter (object not created locally)

Algorithms.java:239:	Found 'DU'-anomaly for variable 'bNeighbours' (lines '239'-'247').

TwitterAnalysis.java:11:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.

TwitterAnalysis.java:16:	Avoid using implementation types like 'HashMap'; use the interface instead

TwitterAnalysis.java:28:	Ensure that resources like this Scanner object are closed after use

TwitterAnalysis.java:35:	New exception is thrown in catch block, original stack trace may be lost

TwitterAnalysis.java:78:	Found 'DD'-anomaly for variable 'flag' (lines '78'-'83').

TwitterAnalysis.java:81:	This for loop can be replaced by a foreach loop

TwitterAnalysis.java:82:	Potential violation of Law of Demeter (method chain calls)

TwitterAnalysis.java:82:	Potential violation of Law of Demeter (method chain calls)

TwitterAnalysis.java:83:	Found 'DD'-anomaly for variable 'flag' (lines '83'-'83').

TwitterAnalysis.java:88:	System.err.print is used

TwitterAnalysis.java:101:	System.err.print is used

TwitterAnalysis.java:104:	System.err.print is used

TwitterAnalysis.java:108:	Position literals first in String comparisons

TwitterAnalysis.java:113:	System.out.println is used

TwitterAnalysis.java:116:	Position literals first in String comparisons

TwitterAnalysis.java:118:	System.out.print is used

TwitterAnalysis.java:121:	System.err.print is used

LoosePackageCoupling	-	No packages or classes specified

## Test Coverage
### Algorithms
| Metric | Coverage |
| ------ | -------- |
|BRANCH_MISSED|13|
|LINE_MISSED|15|
|LINE_COVERED|77|
|BRANCH_COVERED|37|
### AdjacencyListGraph
| Metric | Coverage |
| ------ | -------- |
|BRANCH_MISSED|0|
|LINE_MISSED|4|
|LINE_COVERED|20|
|BRANCH_COVERED|4|
### TwitterAnalysis
| Metric | Coverage |
| ------ | -------- |
|BRANCH_MISSED|0|
|LINE_MISSED|0|
|LINE_COVERED|60|
|BRANCH_COVERED|30|
### AdjacencyMatrixGraph
| Metric | Coverage |
| ------ | -------- |
|BRANCH_MISSED|2|
|LINE_MISSED|5|
|LINE_COVERED|34|
|BRANCH_COVERED|10|

## Checkstyle Results
### `Algorithms.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 6 | None | `Using the '.*' form of import should be avoided - java.util.*.` |
| 31 | 20 | `Expected @param tag for '<T>'.` |
| 32 | None | `Comment matches to-do format 'TODO:'.` |
| 45 | None | `'if' construct must use '{}'s.` |
| 58 | None | `Line is longer than 100 characters (found 102).` |
| 58 | 20 | `Expected @param tag for '<T>'.` |
| 74 | None | `Line is longer than 100 characters (found 142).` |
| 74 | 20 | `Expected @param tag for '<T>'.` |
| 85 | None | `Line is longer than 100 characters (found 101).` |
| 85 | 82 | `'+' is not followed by whitespace.` |
| 85 | 82 | `'+' is not preceded with whitespace.` |
| 103 | None | `Line is longer than 100 characters (found 115).` |
| 105 | 20 | `Expected @param tag for '<T>'.` |
| 119 | None | `Line is longer than 100 characters (found 109).` |
| 125 | None | `Line is longer than 100 characters (found 104).` |
| 125 | 21 | `Expected @param tag for '<T>'.` |
| 149 | None | `Line is longer than 100 characters (found 117).` |
| 151 | 20 | `Expected @param tag for '<T>'.` |
| 180 | None | `Expected @return tag.` |
| 180 | 20 | `Expected @param tag for '<T>'.` |
| 180 | 45 | `Expected @param tag for 'graph'.` |
| 181 | None | `Comment matches to-do format 'TODO:'.` |
| 197 | None | `'if' construct must use '{}'s.` |
| 214 | 20 | `Expected @param tag for '<T>'.` |
| 214 | 97 | `'{' is not preceded with whitespace.` |
| 217 | 40 | `'{' is not preceded with whitespace.` |
| 219 | 66 | `'{' is not preceded with whitespace.` |
| 236 | 20 | `Expected @param tag for '<T>'.` |
| 236 | 99 | `'{' is not preceded with whitespace.` |
| 241 | 40 | `'{' is not preceded with whitespace.` |
| 242 | 41 | `'{' is not preceded with whitespace.` |
| 6 | None | `Using the '.*' form of import should be avoided - java.util.*.` |
| 31 | 20 | `Expected @param tag for '<T>'.` |
| 32 | None | `Comment matches to-do format 'TODO:'.` |
| 45 | None | `'if' construct must use '{}'s.` |
| 58 | None | `Line is longer than 100 characters (found 102).` |
| 58 | 20 | `Expected @param tag for '<T>'.` |
| 74 | None | `Line is longer than 100 characters (found 142).` |
| 74 | 20 | `Expected @param tag for '<T>'.` |
| 85 | None | `Line is longer than 100 characters (found 101).` |
| 85 | 82 | `'+' is not followed by whitespace.` |
| 85 | 82 | `'+' is not preceded with whitespace.` |
| 103 | None | `Line is longer than 100 characters (found 115).` |
| 105 | 20 | `Expected @param tag for '<T>'.` |
| 119 | None | `Line is longer than 100 characters (found 109).` |
| 125 | None | `Line is longer than 100 characters (found 104).` |
| 125 | 21 | `Expected @param tag for '<T>'.` |
| 149 | None | `Line is longer than 100 characters (found 117).` |
| 151 | 20 | `Expected @param tag for '<T>'.` |
| 180 | None | `Expected @return tag.` |
| 180 | 20 | `Expected @param tag for '<T>'.` |
| 180 | 45 | `Expected @param tag for 'graph'.` |
| 181 | None | `Comment matches to-do format 'TODO:'.` |
| 197 | None | `'if' construct must use '{}'s.` |
| 214 | 20 | `Expected @param tag for '<T>'.` |
| 214 | 97 | `'{' is not preceded with whitespace.` |
| 217 | 40 | `'{' is not preceded with whitespace.` |
| 219 | 66 | `'{' is not preceded with whitespace.` |
| 236 | 20 | `Expected @param tag for '<T>'.` |
| 236 | 99 | `'{' is not preceded with whitespace.` |
| 241 | 40 | `'{' is not preceded with whitespace.` |
| 242 | 41 | `'{' is not preceded with whitespace.` |
### `AdjacencyMatrixGraph.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 1 | None | `File does not end with a newline.` |
| 28 | None | `Type Javadoc comment is missing @author tag.` |
| 47 | 39 | `'{' is not preceded with whitespace.` |
| 57 | 24 | `',' is not followed by whitespace.` |
| 57 | 36 | `'-' is not followed by whitespace.` |
| 57 | 36 | `'-' is not preceded with whitespace.` |
| 68 | 52 | `'{' is not preceded with whitespace.` |
| 74 | 20 | `',' is not followed by whitespace.` |
| 89 | 58 | `'{' is not preceded with whitespace.` |
| 93 | None | `'if' construct must use '{}'s.` |
| 110 | None | `Expected @return tag.` |
| 110 | 51 | `Expected @param tag for 'v'.` |
| 110 | 53 | `'{' is not preceded with whitespace.` |
| 115 | 48 | `'{' is not preceded with whitespace.` |
| 116 | None | `'if' construct must use '{}'s.` |
| 116 | 16 | `'(' is followed by whitespace.` |
| 131 | None | `Expected @return tag.` |
| 131 | 41 | `'{' is not preceded with whitespace.` |
| 133 | 47 | `'{' is not preceded with whitespace.` |
| 1 | None | `File does not end with a newline.` |
| 28 | None | `Type Javadoc comment is missing @author tag.` |
| 47 | 39 | `'{' is not preceded with whitespace.` |
| 57 | 24 | `',' is not followed by whitespace.` |
| 57 | 36 | `'-' is not followed by whitespace.` |
| 57 | 36 | `'-' is not preceded with whitespace.` |
| 68 | 52 | `'{' is not preceded with whitespace.` |
| 74 | 20 | `',' is not followed by whitespace.` |
| 89 | 58 | `'{' is not preceded with whitespace.` |
| 93 | None | `'if' construct must use '{}'s.` |
| 110 | None | `Expected @return tag.` |
| 110 | 51 | `Expected @param tag for 'v'.` |
| 110 | 53 | `'{' is not preceded with whitespace.` |
| 115 | 48 | `'{' is not preceded with whitespace.` |
| 116 | None | `'if' construct must use '{}'s.` |
| 116 | 16 | `'(' is followed by whitespace.` |
| 131 | None | `Expected @return tag.` |
| 131 | 41 | `'{' is not preceded with whitespace.` |
| 133 | 47 | `'{' is not preceded with whitespace.` |
### `AdjacencyListGraph.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 8 | 8 | `Unused import - java.util.Comparator.` |
| 29 | None | `Type Javadoc comment is missing @author tag.` |
| 33 | 32 | `'{' is not preceded with whitespace.` |
| 37 | 43 | `'{' is not preceded with whitespace.` |
| 46 | None | `Unused Javadoc tag.` |
| 48 | 39 | `'{' is not preceded with whitespace.` |
| 57 | None | `Unused Javadoc tag.` |
| 60 | 52 | `'{' is not preceded with whitespace.` |
| 72 | 58 | `'{' is not preceded with whitespace.` |
| 74 | 33 | `'{' is not preceded with whitespace.` |
| 92 | None | `Expected @return tag.` |
| 92 | 51 | `Expected @param tag for 'v'.` |
| 92 | 53 | `'{' is not preceded with whitespace.` |
| 104 | None | `Expected @return tag.` |
| 104 | 41 | `'{' is not preceded with whitespace.` |
| 106 | 43 | `'{' is not preceded with whitespace.` |
| 109 | 38 | `',' is not followed by whitespace.` |
| 8 | 8 | `Unused import - java.util.Comparator.` |
| 29 | None | `Type Javadoc comment is missing @author tag.` |
| 33 | 32 | `'{' is not preceded with whitespace.` |
| 37 | 43 | `'{' is not preceded with whitespace.` |
| 46 | None | `Unused Javadoc tag.` |
| 48 | 39 | `'{' is not preceded with whitespace.` |
| 57 | None | `Unused Javadoc tag.` |
| 60 | 52 | `'{' is not preceded with whitespace.` |
| 72 | 58 | `'{' is not preceded with whitespace.` |
| 74 | 33 | `'{' is not preceded with whitespace.` |
| 92 | None | `Expected @return tag.` |
| 92 | 51 | `Expected @param tag for 'v'.` |
| 92 | 53 | `'{' is not preceded with whitespace.` |
| 104 | None | `Expected @return tag.` |
| 104 | 41 | `'{' is not preceded with whitespace.` |
| 106 | 43 | `'{' is not preceded with whitespace.` |
| 109 | 38 | `',' is not followed by whitespace.` |
### `TwitterAnalysis.java`
| Line | Column | Message |
| ---- | ------ | ------- |
| 9 | None | `Using the '.*' form of import should be avoided - java.util.*.` |
| 19 | None | `Line is longer than 100 characters (found 103).` |
| 61 | None | `Line is longer than 100 characters (found 110).` |
| 9 | None | `Using the '.*' form of import should be avoided - java.util.*.` |
| 19 | None | `Line is longer than 100 characters (found 103).` |
| 61 | None | `Line is longer than 100 characters (found 110).` |

