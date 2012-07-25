(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['ec'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  
  return "\n            <ul class=\"nav pull-right\">\n                <li class=\"divider-vertical\"></li>\n                <li><a class=\"ec-status high-priority\" href=\"#\">High Priority</a></li>\n            </ul>\n        ";}

function program3(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n            <div class=\"alert\">\n                <i class=\"icon-warning-sign\"></i>\n                <strong>Alert: ";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong>\n            </div>\n        ";
  return buffer;}

function program5(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                        ";
  stack1 = depth0.isFemale;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(8, program8, data),fn:self.program(6, program6, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    ";
  return buffer;}
function program6(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                            <div class=\"ec-child girl\">\n                                <div style=\"text-align: center;\">\n                                    <span style=\"font-weight: bold\">";
  foundHelper = helpers.age;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.age; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br>\n                                    Girl\n                                </div>\n                            </div>\n                        ";
  return buffer;}

function program8(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                            <div class=\"ec-child boy\">\n                                <div style=\"text-align: center;\">\n                                    <span style=\"font-weight: bold;\">";
  foundHelper = helpers.age;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.age; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br>\n                                    Boy\n                                </div>\n                            </div>\n                        ";
  return buffer;}

function program10(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                                    <div class=\"ec-timeline-details-component row-fluid\">\n                                        <div class=\"span1\">\n                                            <i class=\"icon-filter\"></i>\n                                        </div>\n                                            <div class=\"span7\">\n                                                <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(11, program11, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                            </div>\n                                        <div class=\"span4 pull-right\" style=\"text-align: right\">\n                                            ";
  foundHelper = helpers.date;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.date; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                                        </div>\n                                    </div>\n                                ";
  return buffer;}
function program11(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                                    ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "<br>\n                                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand list\" href=\"#\">EC</a>\n\n        ";
  stack1 = depth0.isHighPriority;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"ec-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"ec-details unstyled\">\n                    <li class=\"ec-name\">";
  foundHelper = helpers.wifeName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.wifeName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.village;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.village; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + ", ";
  foundHelper = helpers.subcenter;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.subcenter; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>EC: ";
  foundHelper = helpers.ecNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.ecNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2\" style=\"float: right;\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"ec-container container-fluid\">\n        ";
  stack1 = depth0.alerts;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(3, program3, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        <div class=\"alert alert-info\">\n            <i class=\"icon-check\"></i>\n            <strong>To do: Follow up on malaria test</strong>\n        </div>\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"ec-container container-fluid\">\n        <div class=\"ec-name row-fluid\">\n            EC Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Family Planning</div>\n                    <div class=\"beneficiary-detail-content\">\n                        <i class=\"icon-ok\"></i><span class=\"family-planning-current\">";
  foundHelper = helpers.currentMethod;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.currentMethod; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br/>\n                        <a class=\"btn family-planning-button btn-large\" href=\"family_planning.html\">Family Planning Tool</a>\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n                </div>\n\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Children</div>\n                    <div class=\"beneficiary-detail-content\">\n                    ";
  stack1 = depth0.children;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(5, program5, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n\n                    <div id=\"ec-timeline\" class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-header\" data-toggle=\"collapse\" data-target=\"#nonono\">\n                            <i class=\"icon-calendar\"></i> View EC Timeline\n                            <a><i class=\"toggle-indicator icon-chevron-down pull-right\"></i></a>\n                        </div>\n                        <div class=\"beneficiary-detail-content collapse\" id=\"nonono\">\n                            <div class=\"divider divider-padding-both\"></div>\n                            <div class=\"ec-timeline-details container-fluid\">\n                                ";
  stack1 = depth0.timeline;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(10, program10, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                        </div>\n                    </div>\n                    <script type=\"text/javascript\">\n                        toggleIconDuringCollapse(\"#ec-timeline\", \".toggle-indicator\", \"icon-chevron-down\", \"icon-chevron-up\");\n                    </script>\n\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"ec-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"contactButton\">Contact</a>\n            </div>\n            <div class=\"span8\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"newVisitButton\">New Visit</a>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['ec_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " class=\"ec-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"ec-details unstyled\">\n                    <li class=\"ec-name\">";
  foundHelper = helpers.wifeName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.wifeName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.village;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.village; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>EC#: ";
  foundHelper = helpers.ecNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.ecNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                    ";
  stack1 = depth0.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return "\n                        High Risk\n                    ";}

  stack1 = depth0.ec;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['home'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression;


  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <span class=\"brand list\" href=\"#\">";
  foundHelper = helpers.anmName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.anmName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span>\n\n        <ul class=\"nav pull-right\">\n            <li class=\"divider-vertical list\"></li>\n            <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n        </ul>\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"menu-button-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"menu-button span6\" id=\"workplanButton\">\n                <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                Workplan\n            </div>\n            <div class=\"menu-button span6\" id=\"inboxButton\">\n                <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                Inbox\n            </div>\n        </div>\n        <div class=\"row-fluid menu-button-row-padding\">\n            <div class=\"menu-button span6\" id=\"myStatsButton\">\n                <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                My Stats\n            </div>\n            <div class=\"menu-button span6\" id=\"reportsButton\">\n                <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                Reporting\n            </div>\n        </div>\n        <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-ec.png\">\n            </div>\n            <div class=\"span9\">Eligible Couple</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.eligibleCoupleCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.eligibleCoupleCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-anc.png\">\n            </div>\n            <div class=\"span9\">ANC</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.ancCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.ancCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-pnc.png\">\n            </div>\n            <div class=\"span9\">PNC</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.pncCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.pncCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n        <div class=\"menu-option-divider\"></div>\n\n        <div class=\"menu-option row-fluid\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-child.png\">\n            </div>\n            <div class=\"span9\">Child</div>\n            <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.childCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.childCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
})();