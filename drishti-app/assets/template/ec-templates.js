(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['ec'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  
  return "\n            <ul class=\"nav pull-right\">\n                <li class=\"divider-vertical\"></li>\n                <li><a class=\"ec-status high-priority\" href=\"#\">High Priority</a></li>\n            </ul>\n        ";}

function program3(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n            <div class=\"alert\">\n                <i class=\"icon-warning-sign\"></i>\n                <strong>Alert: ";
  foundHelper = helpers.message;
  stack1 = foundHelper || depth0.message;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "message", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong>\n            </div>\n        ";
  return buffer;}

function program5(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n                        ";
  foundHelper = helpers.isFemale;
  stack1 = foundHelper || depth0.isFemale;
  stack2 = helpers['if'];
  tmp1 = self.program(6, program6, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.program(8, program8, data);
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    ";
  return buffer;}
function program6(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"ec-child girl\">\n                                <div style=\"text-align: center;\">\n                                    <span style=\"font-weight: bold\">";
  foundHelper = helpers.age;
  stack1 = foundHelper || depth0.age;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "age", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br>\n                                    Girl\n                                </div>\n                            </div>\n                        ";
  return buffer;}

function program8(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"ec-child boy\">\n                                <div style=\"text-align: center;\">\n                                    <span style=\"font-weight: bold;\">";
  foundHelper = helpers.age;
  stack1 = foundHelper || depth0.age;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "age", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br>\n                                    Boy\n                                </div>\n                            </div>\n                        ";
  return buffer;}

function program10(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n                                    <div class=\"ec-timeline-details-component row-fluid\">\n                                        <div class=\"span1\">\n                                            <i class=\"icon-filter\"></i>\n                                        </div>\n                                            <div class=\"span7\">\n                                                <strong>";
  foundHelper = helpers.title;
  stack1 = foundHelper || depth0.title;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "title", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                ";
  foundHelper = helpers.details;
  stack1 = foundHelper || depth0.details;
  stack2 = helpers.each;
  tmp1 = self.program(11, program11, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                            </div>\n                                        <div class=\"span4 pull-right\" style=\"text-align: right\">\n                                            ";
  foundHelper = helpers.date;
  stack1 = foundHelper || depth0.date;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "date", { hash: {} }); }
  buffer += escapeExpression(stack1) + "\n                                        </div>\n                                    </div>\n                                ";
  return buffer;}
function program11(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                                    ";
  stack1 = depth0;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "this", { hash: {} }); }
  buffer += escapeExpression(stack1) + "<br>\n                                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"name ellipsis\">EC</span>\n        </a>\n\n        ";
  foundHelper = helpers.isHighPriority;
  stack1 = foundHelper || depth0.isHighPriority;
  stack2 = helpers['if'];
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"ec-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"ec-details unstyled\">\n                    <li class=\"ec-name\">";
  foundHelper = helpers.wifeName;
  stack1 = foundHelper || depth0.wifeName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "wifeName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.village;
  stack1 = foundHelper || depth0.village;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "village", { hash: {} }); }
  buffer += escapeExpression(stack1) + ", ";
  foundHelper = helpers.subcenter;
  stack1 = foundHelper || depth0.subcenter;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "subcenter", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>EC: ";
  foundHelper = helpers.ecNumber;
  stack1 = foundHelper || depth0.ecNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ecNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2\" style=\"float: right;\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"ec-container container-fluid\">\n        ";
  foundHelper = helpers.alerts;
  stack1 = foundHelper || depth0.alerts;
  stack2 = helpers.each;
  tmp1 = self.program(3, program3, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        <div class=\"alert alert-info\">\n            <i class=\"icon-check\"></i>\n            <strong>To do: Follow up on malaria test</strong>\n        </div>\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"ec-container container-fluid\">\n        <div class=\"ec-name row-fluid\">\n            EC Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Family Planning</div>\n                    <div class=\"beneficiary-detail-content\">\n                        <i class=\"icon-ok\"></i><span class=\"family-planning-current\">";
  foundHelper = helpers.currentMethod;
  stack1 = foundHelper || depth0.currentMethod;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "currentMethod", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br/>\n                        <a class=\"btn family-planning-button btn-large\" href=\"family_planning.html\">Family Planning Tool</a>\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n                </div>\n\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Children</div>\n                    <div class=\"beneficiary-detail-content\">\n                    ";
  foundHelper = helpers.children;
  stack1 = foundHelper || depth0.children;
  stack2 = helpers.each;
  tmp1 = self.program(5, program5, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n\n                    <div id=\"ec-timeline\" class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-header\" data-toggle=\"collapse\" data-target=\"#nonono\">\n                            <i class=\"icon-calendar\"></i> View EC Timeline\n                            <a><i class=\"toggle-indicator icon-chevron-down pull-right\"></i></a>\n                        </div>\n                        <div class=\"beneficiary-detail-content collapse\" id=\"nonono\">\n                            <div class=\"divider divider-padding-both\"></div>\n                            <div class=\"ec-timeline-details container-fluid\">\n                                ";
  foundHelper = helpers.timeline;
  stack1 = foundHelper || depth0.timeline;
  stack2 = helpers.each;
  tmp1 = self.program(10, program10, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                        </div>\n                    </div>\n                    <script type=\"text/javascript\">\n                        toggleIconDuringCollapse(\"#ec-timeline\", \".toggle-indicator\", \"icon-chevron-down\", \"icon-chevron-up\");\n                    </script>\n\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"ec-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"contactButton\">Contact</a>\n            </div>\n            <div class=\"span8\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"newVisitButton\">New Visit</a>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['ec_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  stack1 = foundHelper || depth0.caseId;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "caseId", { hash: {} }); }
  buffer += escapeExpression(stack1) + " class=\"ec-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"ec-details unstyled\">\n                    <li class=\"ec-name\">";
  foundHelper = helpers.wifeName;
  stack1 = foundHelper || depth0.wifeName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "wifeName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.village;
  stack1 = foundHelper || depth0.village;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "village", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>EC#: ";
  foundHelper = helpers.ecNumber;
  stack1 = foundHelper || depth0.ecNumber;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ecNumber", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                    ";
  foundHelper = helpers.isHighRisk;
  stack1 = foundHelper || depth0.isHighRisk;
  stack2 = helpers['if'];
  tmp1 = self.program(2, program2, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return "\n                        High Risk\n                    ";}

  foundHelper = helpers.ec;
  stack1 = foundHelper || depth0.ec;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['home'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;


  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"name ellipsis\">";
  foundHelper = helpers.anmName;
  stack1 = foundHelper || depth0.anmName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "anmName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span>\n        </a>\n\n        <ul class=\"nav pull-right\">\n            <li class=\"divider-vertical list\"></li>\n            <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n        </ul>\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"menu-button-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"menu-button span6\" id=\"workplanButton\">\n                <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                Workplan\n            </div>\n            <div class=\"menu-button span6\" id=\"inboxButton\">\n                <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                Inbox\n            </div>\n        </div>\n        <div class=\"row-fluid menu-button-row-padding\">\n            <div class=\"menu-button span6\" id=\"myStatsButton\">\n                <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                My Stats\n            </div>\n            <div class=\"menu-button span6\" id=\"reportsButton\">\n                <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                Reporting\n            </div>\n        </div>\n        <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-ec.png\">\n            </div>\n            <div class=\"span9\">Eligible Couple</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.eligibleCoupleCount;
  stack1 = foundHelper || depth0.eligibleCoupleCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "eligibleCoupleCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-anc.png\">\n            </div>\n            <div class=\"span9\">ANC</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.ancCount;
  stack1 = foundHelper || depth0.ancCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ancCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-pnc.png\">\n            </div>\n            <div class=\"span9\">PNC</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.pncCount;
  stack1 = foundHelper || depth0.pncCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-child.png\">\n            </div>\n            <div class=\"span9\">Child</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.childCount;
  stack1 = foundHelper || depth0.childCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "childCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['sidebar'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;


  buffer += "<div id=\"sidepanel\" class=\"sidepanel affected-by-sidepanel\">\n    <div class=\"menu-background\">\n        <div class=\"navbar navbar-fixed-top navbar-sidebar\">\n            <div class=\"navbar-inner nav-sidebar\">\n                <span class=\"brand in-navbar in-sidebar\" href=\"#\">";
  foundHelper = helpers.anmName;
  stack1 = foundHelper || depth0.anmName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "anmName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span>\n\n                <ul class=\"nav pull-right\">\n                    <li class=\"divider-vertical in-navbar-sidebar\"></li>\n                    <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n                </ul>\n            </div>\n        </div>\n\n        <div class=\"content sidebar-content\">\n            <div class=\"menu-button-container container-fluid\">\n                <div class=\"row-fluid\">\n                    <div class=\"menu-button span6\" id=\"workplanButton\">\n                        <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                        Workplan\n                    </div>\n                    <div class=\"menu-button span6\" id=\"inboxButton\">\n                        <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                        Inbox\n                    </div>\n                </div>\n                <div class=\"row-fluid menu-button-row-padding\">\n                    <div class=\"menu-button span6\" id=\"myStatsButton\">\n                        <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                        My Stats\n                    </div>\n                    <div class=\"menu-button span6\" id=\"reportsButton\">\n                        <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                        Reporting\n                    </div>\n                </div>\n                <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-ec.png\">\n                    </div>\n                    <div class=\"span9\">Eligible Couple</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.eligibleCoupleCount;
  stack1 = foundHelper || depth0.eligibleCoupleCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "eligibleCoupleCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-anc.png\">\n                    </div>\n                    <div class=\"span9\">ANC</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.ancCount;
  stack1 = foundHelper || depth0.ancCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "ancCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-pnc.png\">\n                    </div>\n                    <div class=\"span9\">PNC</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.pncCount;
  stack1 = foundHelper || depth0.pncCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "pncCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-child.png\">\n                    </div>\n                    <div class=\"span9\">Child</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.childCount;
  stack1 = foundHelper || depth0.childCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "childCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div id=\"mainpanel-overlay\" class=\"affected-by-sidepanel\">\n</div>\n";
  return buffer;});
templates['workplan'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression, blockHelperMissing=helpers.blockHelperMissing;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\n        <tr class=\"workplanElement\" data-village=";
  foundHelper = helpers.name;
  stack1 = foundHelper || depth0.name;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "name", { hash: {} }); }
  buffer += escapeExpression(stack1) + ">\n            <td style=\"white-space: nowrap\"><span class=\"summary\">";
  foundHelper = helpers.name;
  stack1 = foundHelper || depth0.name;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "name", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span><br/><span class=\"village-breakdown\">";
  foundHelper = helpers.reminderCount;
  stack1 = foundHelper || depth0.reminderCount;
  foundHelper = helpers.ifNotZero;
  stack2 = foundHelper || depth0.ifNotZero;
  tmp1 = self.program(2, program2, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  if(foundHelper && typeof stack2 === functionType) { stack1 = stack2.call(depth0, stack1, tmp1); }
  else { stack1 = blockHelperMissing.call(depth0, stack2, stack1, tmp1); }
  if(stack1 || stack1 === 0) { buffer += stack1; }
  foundHelper = helpers.alertCount;
  stack1 = foundHelper || depth0.alertCount;
  foundHelper = helpers.ifNotZero;
  stack2 = foundHelper || depth0.ifNotZero;
  tmp1 = self.program(4, program4, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  if(foundHelper && typeof stack2 === functionType) { stack1 = stack2.call(depth0, stack1, tmp1); }
  else { stack1 = blockHelperMissing.call(depth0, stack2, stack1, tmp1); }
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</span>\n            </td>\n        </tr>\n    ";
  return buffer;}
function program2(depth0,data) {
  
  var buffer = "", stack1;
  foundHelper = helpers.reminderCount;
  stack1 = foundHelper || depth0.reminderCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "reminderCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " Reminders, ";
  return buffer;}

function program4(depth0,data) {
  
  var buffer = "", stack1;
  foundHelper = helpers.alertCount;
  stack1 = foundHelper || depth0.alertCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "alertCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " Alerts";
  return buffer;}

  buffer += "<table class=\"table table-bordered\">\n    <tbody>\n    <tr>\n        <td style=\"white-space: nowrap\"><i class=\"summary-icon icon-user\"></i><span class=\"summary\">";
  foundHelper = helpers.totalAlertCount;
  stack1 = foundHelper || depth0.totalAlertCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "totalAlertCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " New Alerts</span></td>\n    </tr>\n    <tr>\n        <td style=\"white-space: nowrap\"><i class=\"summary-icon icon-user\"></i><span class=\"summary\">";
  foundHelper = helpers.totalReminderCount;
  stack1 = foundHelper || depth0.totalReminderCount;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "totalReminderCount", { hash: {} }); }
  buffer += escapeExpression(stack1) + " New Reminder</span></td>\n    </tr>\n    </tbody>\n</table>\n\n<h3 class=\"village-header\">Villages</h3>\n\n<table class=\"table table-bordered\">\n    <tbody id=\"workplan-list\">\n    ";
  foundHelper = helpers.villages;
  stack1 = foundHelper || depth0.villages;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </tbody>\n</table>\n";
  return buffer;});
templates['workplan_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, stack2, foundHelper, tmp1, self=this, functionType="function", helperMissing=helpers.helperMissing, undef=void 0, escapeExpression=this.escapeExpression;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n        <div class=\"alert high-priority\">\n            <div class=\"row-fluid\">\n                <div class=\"span1\"><i class=\"icon-warning-sign\"></i></div>\n                <div class=\"span11\">\n                    <div class=\"beneficiaryName\">";
  foundHelper = helpers.beneficiaryName;
  stack1 = foundHelper || depth0.beneficiaryName;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "beneficiaryName", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                    <div class=\"dueDate\">";
  foundHelper = helpers.dueDate;
  stack1 = foundHelper || depth0.dueDate;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "dueDate", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                    <div class=\"detail\">";
  foundHelper = helpers.description;
  stack1 = foundHelper || depth0.description;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "description", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar ellipsis affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">Workplan: ";
  foundHelper = helpers.village;
  stack1 = foundHelper || depth0.village;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "village", { hash: {} }); }
  buffer += escapeExpression(stack1) + "</span>\n        </a>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content\">\n    <h3 class=\"priority-header\">Soon</h3>\n    ";
  foundHelper = helpers.alerts;
  stack1 = foundHelper || depth0.alerts;
  stack2 = helpers.each;
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    <div class=\"alert low-priority\">\n            <div class=\"row-fluid\">\n                <div class=\"span1\"><i class=\"icon-warning-sign\"></i></div>\n                <div class=\"span11\">\n                    <div class=\"beneficiaryName\">Akarishma</div>\n                    <div class=\"villageName\">Bherya</div>\n                    <div class=\"detail\">Lorem ipsum dolor sit amet</div>\n                </div>\n            </div>\n    </div>\n</div>\n";
  return buffer;});
})();