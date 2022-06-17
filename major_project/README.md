Input API: TheGuardian <br>
Output API: Pastebin <br> 
Claimed Tier: Distinction <br>
Credit Optional Feature 1: Keyboard shortcuts and tabbing navigation <br>
Credit Optional Feature 2: Theme music <br>
Distinction Optional Feature: Progress Indicator for API calls

Milestone 1 Submission: <br>
    SHA: 76f268e <br>
    URI: https://github.sydney.edu.au/abur2581/SCD2_2022/commit/76f268e4798478d79130721917653a176352f470 <br>
Milestone 1 Re-Submission: <br>
    SHA: 02f5b31 <br>
    URI: https://github.sydney.edu.au/abur2581/SCD2_2022/commit/02f5b314b8ca1c01e30f8749f9675b4903ca5862 <br>
Milestone 2 Submission: <br>
    SHA: 5c7d1cd <br>
    URI: https://github.sydney.edu.au/abur2581/SCD2_2022/commit/5c7d1cd5bc759093c424eec7c9ad8eb147fc818b <br>
Milestone 2 Re-Submission: <br>
    SHA: cdb6f14 <br>
    URI: https://github.sydney.edu.au/abur2581/SCD2_2022/commit/cdb6f14f71df25b1262063855cab5208e480b0cf <br>
Exam Base Commit: <br>
    SHA: cdb6f14 <br>
    URI: https://github.sydney.edu.au/abur2581/SCD2_2022/commit/cdb6f14f71df25b1262063855cab5208e480b0cf <br>
Exam Submission Commit: <br>
    SHA: 76b630d <br>
    URI: https://github.sydney.edu.au/abur2581/SCD2_2022/commit/76b630d746c180192ca024bcf8adc9aeb56d571a <br>

Credit Extra Features:<br>
Ability to toggle between elements using Tab, arrow keys to navigate list views and enter to select current selection. <br>
Shortcuts for functionality as follows: Ctrl + <br>
L for lookup button <br>
C for tag query clear <br>
G for generating report <br>
P for music play/pause <br>
V for cache clear <br>

Theme song that plays on a loop, with requisite play/pause button. <br>

Distinction Extra Feature: <br>
Progress indicator will spin with api call is initiated, and then turn to done with a check mark when the call is finished. <br>

**Exam implementation details:** <br>
New shortcuts have been added: <br>
Ctrl + J to add a selected article to the saved list <br>
Ctrl + K to remove a selected article from the saved list <br>

In order to open an article using its URL: <br> 
- Double-click the article from the list or single click/hover using tab/arrow keys + press enter <br>

Single clicks will now only highlight the selected article for the articles-with-tag and the saved articles list views <br>
to accommodate adding/removing to the saved list <br>

In order to save an article to your saved list: <br>
- Single left-click or hover using tab+arrow keys on the article you are interested in (from the article results list) <br>
- Use the "Add to saved list" button or use the corresponding shortcut <br>

In order to delete an article from your saved list: <br>
 - Single left-click or hover using tab+arrow keys on the article you are interested in removing (from the saved articles list) <br>
 - Use the "Delete from saved list" button or use the corresponding shortcut <br>

Note: newly added articles to the saved list will be added to the very end of the list every time <br>

Citations: <br>
Task 3 for inspiration for setting up GUI and keyboard shortcuts <br>
HelloHTTP from the modules for most of the HTTP handling in both input/output handler classes. <br> 
Week 8 Tutorial - HelloJavaFXWorkers for guidance on setting up concurrency
https://hc.apache.org/httpcomponents-client-5.1.x/quickstart.html# - Apache docs for setting up Apache HTTP client <br> 
https://dova-s.jp/EN/bgm/play12459.html - おもちゃのダンス @ フリ BGM by DOVA-SYNDROME, renamed to amelia_watson_bgm.mp3 in ./src/main/resources <br>
https://edencoding.com/resources/css_properties/fx-background-color/ - information on setting the background colour/colour effects <br>