(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['anc_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data,depth1) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n        <div data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth1.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" class=\"alert profile-alert todo\">\n            <i class=\"icon-warning-sign\"></i>\n            <strong>";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong>\n        </div>\n        ";
  return buffer;}

function program3(depth0,data,depth1) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n    <div data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth1.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" class=\"alert alert-info todo\">\n        <i class=\"icon-check\"></i>\n        <strong>";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong>\n    </div>\n    ";
  return buffer;}

function program5(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"text-center big-text high-risk\">\n                                <i class=\"icon-circle-arrow-up\" style=\"margin-top: 3px;\"></i> High Risk</span>\n                            </div>\n                            <div class=\"beneficiary-detail-footer risk-detail-text\">";
  stack1 = depth0.pregnancyDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.riskDetail;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                        ";
  return buffer;}

function program7(depth0,data) {
  
  
  return "\n                            <div class=\"text-center big-text normal-risk\">Normal Risk</div>\n                        ";}

function program9(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                        <div class=\"container-section-component row-fluid\">\n                            <div class=\"span1\">\n                                <i class=\"icon-filter\"></i>\n                            </div>\n                            <div class=\"span7\">\n                                <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(10, program10, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                            <div class=\"span4 pull-right text-right\">\n                                ";
  foundHelper = helpers.date;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.date; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                            </div>\n                        </div>\n                        ";
  return buffer;}
function program10(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "<br>\n                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">ANC</span>\n        </a>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical\"></li>\n            <li><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        ";
  stack1 = depth0.alerts;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program1, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.womanName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  stack1 = depth0.location;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.villageName;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + ", ";
  stack1 = depth0.location;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.subcenter;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</li>\n                    <li>TC: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div id=\"todos\" class=\"registry-container container-fluid\">\n    ";
  stack1 = depth0.todos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program3, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n\n    <div class=\"divider\"></div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        ANC Summary\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                        ";
  stack1 = depth0.pregnancyDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(7, program7, data),fn:self.program(5, program5, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  stack1 = depth0.pregnancyDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.monthsPregnant;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Months Pregnant</span>\n                        </div>\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  stack1 = depth0.pregnancyDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.edd;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">EDD</span>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Delivery Plan\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding container-section\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"span1\"><i class=\"icon-check\"></i></div>\n                <div class=\"span3 big-text\">Facility</div>\n                <div class=\"span8\">";
  stack1 = depth0.facilityDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.facility;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n            </div>\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"span1\"><i class=\"icon-check\"></i></div>\n                <div class=\"span3 big-text\">Contact</div>\n                <div class=\"span8\">";
  stack1 = depth0.facilityDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.contact;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n            </div>\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"span1\"><i class=\"icon-check\"></i></div>\n                <div class=\"span3 big-text\">ASHA</div>\n                <div class=\"span8\">";
  stack1 = depth0.facilityDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.ashaName;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Timeline\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                    <div class=\"container-section container-fluid\">\n                        ";
  stack1 = depth0.timelineEvents;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(9, program9, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['anc_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.womanName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.villageName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.villageName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>Thaayi #: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                ";
  stack1 = depth0.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return " High Risk ";}

  stack1 = depth0.anc;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['ec_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  
  return "\n            <ul class=\"nav pull-right affected-by-sidepanel\">\n                <li class=\"divider-vertical\"></li>\n                <li><a class=\"ec-status high-priority\" href=\"#\">High Priority</a></li>\n            </ul>\n        ";}

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
  buffer += "\n                                    <div class=\"container-section-component row-fluid\">\n                                        <div class=\"span1\">\n                                            <i class=\"icon-filter\"></i>\n                                        </div>\n                                            <div class=\"span7\">\n                                                <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(11, program11, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                            </div>\n                                        <div class=\"span4 pull-right text-right\">\n                                            ";
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

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">EC</span>\n        </a>\n\n        ";
  stack1 = depth0.isHighPriority;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
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
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        ";
  stack1 = depth0.alerts;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(3, program3, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        <div class=\"alert alert-info\">\n            <i class=\"icon-check\"></i>\n            <strong>To do: Follow up on malaria test</strong>\n        </div>\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            EC Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Family Planning</div>\n                    <div class=\"beneficiary-detail-content\">\n                        <i class=\"icon-ok\"></i><span class=\"family-planning-current\">";
  foundHelper = helpers.currentMethod;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.currentMethod; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br/>\n                        <a class=\"btn family-planning-button btn-large\" href=\"family_planning.html\">Family Planning Tool</a>\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n                </div>\n\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Children</div>\n                    <div class=\"beneficiary-detail-content\">\n                    ";
  stack1 = depth0.children;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(5, program5, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n\n                    <div id=\"ec-timeline\" class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-header\" data-toggle=\"collapse\" data-target=\"#timeline-toggled-content\">\n                            <i class=\"icon-calendar\"></i> View EC Timeline\n                            <a><i class=\"toggle-indicator icon-chevron-down pull-right\"></i></a>\n                        </div>\n                        <div class=\"beneficiary-detail-content collapse\" id=\"timeline-toggled-content\">\n                            <div class=\"divider divider-padding-both\"></div>\n                            <div class=\"container-section container-fluid\">\n                                ";
  stack1 = depth0.timeline;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(10, program10, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                        </div>\n                    </div>\n                    <script type=\"text/javascript\">\n                        toggleIconDuringCollapse(\"#ec-timeline\", \".toggle-indicator\", \"icon-chevron-down\", \"icon-chevron-up\");\n                    </script>\n\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"contactButton\">Contact</a>\n            </div>\n            <div class=\"span8\">\n                <a class=\"ec-actions-button btn btn-large\" id=\"newVisitButton\">New Visit</a>\n            </div>\n        </div>\n    </div>\n</div>\n";
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
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
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


  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"name ellipsis\">";
  foundHelper = helpers.anmName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.anmName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span>\n        </a>\n\n        <ul class=\"nav pull-right\">\n            <li class=\"divider-vertical list\"></li>\n            <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n        </ul>\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"menu-button-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"menu-button span6\" id=\"workplanButton\">\n                <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                Workplan\n            </div>\n            <div class=\"menu-button span6\" id=\"inboxButton\">\n                <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                Inbox\n            </div>\n        </div>\n        <div class=\"row-fluid menu-button-row-padding\">\n            <div class=\"menu-button span6\" id=\"myStatsButton\">\n                <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                My Stats\n            </div>\n            <div class=\"menu-button span6\" id=\"reportsButton\">\n                <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                Reporting\n            </div>\n        </div>\n        <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n            <div class=\"span1\">\n                <img src=\"../img/icons/nav-ec.png\">\n            </div>\n            <div class=\"span9\">Eligible Couple</div>\n            <div class=\"span2 pull-text-completely-right\">";
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
templates['pnc_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <div class=\"text-center big-text high-risk\">\n                                <i class=\"icon-circle-arrow-up\" style=\"margin-top: 3px;\"></i> High Risk</span>\n                            </div>\n                            <div class=\"beneficiary-detail-footer risk-detail-text\">";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.riskDetail;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                        ";
  return buffer;}

function program3(depth0,data) {
  
  
  return "\n                            <div class=\"text-center big-text normal-risk\">Normal Risk</div>\n                        ";}

function program5(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                    <div class=\"row-fluid\">\n                          <b> Delivery Complications </b>\n                          <ol>\n                              ";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.deliveryComplications;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(6, program6, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                          </ol>\n                    </div>\n                    ";
  return buffer;}
function program6(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                <li>\n                                ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "\n                                </li>\n                              ";
  return buffer;}

function program8(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                        <div class=\"container-section-component row-fluid\">\n                            <div class=\"span1\">\n                                <i class=\"icon-filter\"></i>\n                            </div>\n                            <div class=\"span7\">\n                                <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(9, program9, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                            <div class=\"span4 pull-right text-right\">\n                                ";
  foundHelper = helpers.date;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.date; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                            </div>\n                        </div>\n                        ";
  return buffer;}
function program9(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "<br>\n                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">PNC</span>\n        </a>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical\"></li>\n            <li><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/mother.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.womanName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  stack1 = depth0.location;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.villageName;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + ", ";
  stack1 = depth0.location;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.subcenter;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</li>\n                    <li>Thayi: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n</div>\n\n\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Summary\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                        ";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(3, program3, data),fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.daysPostpartum;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Days Postpartum</span>\n                        </div>\n                        <div class=\"span6 text-center\">\n                            <span class=\"big-text\">";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.dateOfDelivery;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Date of Delivery</span>\n                        </div>\n                    </div>\n                    <div class=\"divider\"></div>\n                    ";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.deliveryComplications;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(5, program5, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        PNC Plan\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        42 day checklist\n    </div>\n</div>\n\n<div class=\"registry-container container-fluid\">\n    <div class=\"big-text row-fluid detail-section-header\">\n        Timeline\n    </div>\n    <div class=\"well well-for-beneficiary-details\">\n        <div class=\"container-fluid container-no-padding\">\n            <div class=\"row-fluid beneficiary-detail-component\">\n                <div class=\"beneficiary-detail-content\">\n                    <div class=\"container-section container-fluid\">\n                        ";
  stack1 = depth0.timelineEvents;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(8, program8, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
  return buffer;});
templates['pnc_list'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n    <div class=\"row-fluid\">\n        <a data-caseId=";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span9\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
  foundHelper = helpers.womanName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.womanName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>";
  foundHelper = helpers.villageName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.villageName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>Thaayi #: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk pull-text-completely-right\">\n                ";
  stack1 = depth0.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-bottom\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return " High Risk ";}

  stack1 = depth0.pnc;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['sidepanel'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression;


  buffer += "<div id=\"sidepanel\" class=\"sidepanel affected-by-sidepanel\">\n    <div class=\"menu-background\">\n        <div class=\"navbar navbar-fixed-top navbar-sidepanel\">\n            <div class=\"navbar-inner nav-sidepanel\">\n                <span class=\"brand in-navbar in-sidepanel\" href=\"#\">";
  foundHelper = helpers.anmName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.anmName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span>\n\n                <ul class=\"nav pull-right\">\n                    <li class=\"divider-vertical in-navbar-sidepanel\"></li>\n                    <li><i class=\"navbar-icon icon-cog icon-white\" href=\"#\"></i></li>\n                </ul>\n            </div>\n        </div>\n\n        <div class=\"content sidepanel-content\">\n            <div class=\"menu-button-container container-fluid\">\n                <div class=\"row-fluid\">\n                    <div class=\"menu-button span6\" id=\"workplanButton\">\n                        <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                        Workplan\n                    </div>\n                    <div class=\"menu-button span6\" id=\"inboxButton\">\n                        <img src=\"../img/icons/nav-inbox.png\" class=\"menu-icon\"><br>\n                        Inbox\n                    </div>\n                </div>\n                <div class=\"row-fluid menu-button-row-padding\">\n                    <div class=\"menu-button span6\" id=\"myStatsButton\">\n                        <img src=\"../img/icons/nav-activity.png\" class=\"menu-icon\"><br>\n                        My Stats\n                    </div>\n                    <div class=\"menu-button span6\" id=\"reportsButton\">\n                        <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                        Reporting\n                    </div>\n                </div>\n                <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-ec.png\">\n                    </div>\n                    <div class=\"span9\">Eligible Couple</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.eligibleCoupleCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.eligibleCoupleCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\" id=\"ancMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-anc.png\">\n                    </div>\n                    <div class=\"span9\">ANC</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.ancCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.ancCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\" id=\"pncMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-pnc.png\">\n                    </div>\n                    <div class=\"span9\">PNC</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.pncCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.pncCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"menu-option-divider\"></div>\n\n                <div class=\"menu-option row-fluid\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-child.png\">\n                    </div>\n                    <div class=\"span9\">Child</div>\n                    <div class=\"span2 pull-text-completely-right\">";
  foundHelper = helpers.childCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.childCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n\n<div id=\"mainpanel-overlay\" class=\"affected-by-sidepanel\">\n</div>\n";
  return buffer;});
templates['workplan'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n        <tr class=\"workplanElement\" data-village=";
  foundHelper = helpers.name;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.name; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + ">\n            <td><span class=\"summary\">";
  foundHelper = helpers.name;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.name; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br/><span class=\"village-breakdown\">";
  stack1 = depth0.reminderCount;
  foundHelper = helpers.ifNotZero;
  stack1 = foundHelper ? foundHelper.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)}) : helperMissing.call(depth0, "ifNotZero", stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  stack1 = depth0.alertCount;
  foundHelper = helpers.ifNotZero;
  stack1 = foundHelper ? foundHelper.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(4, program4, data)}) : helperMissing.call(depth0, "ifNotZero", stack1, {hash:{},inverse:self.noop,fn:self.program(4, program4, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</span>\n            </td>\n        </tr>\n    ";
  return buffer;}
function program2(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  foundHelper = helpers.reminderCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.reminderCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " Reminders, ";
  return buffer;}

function program4(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  foundHelper = helpers.alertCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.alertCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " Alerts";
  return buffer;}

  buffer += "<table class=\"table table-bordered\">\n    <tbody>\n    <tr>\n        <td><i class=\"summary-icon icon-user\"></i><span class=\"summary\">";
  foundHelper = helpers.totalAlertCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.totalAlertCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " New Alerts</span></td>\n    </tr>\n    <tr>\n        <td><i class=\"summary-icon icon-user\"></i><span class=\"summary\">";
  foundHelper = helpers.totalReminderCount;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.totalReminderCount; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " New Reminder</span></td>\n    </tr>\n    </tbody>\n</table>\n\n<h3 class=\"village-header\">Villages</h3>\n\n<table class=\"table table-bordered\">\n    <tbody id=\"workplan-list\">\n    ";
  stack1 = depth0.villages;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </tbody>\n</table>\n";
  return buffer;});
templates['workplan_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n        <div class=\"alert high-priority\">\n            <div class=\"row-fluid\">\n                <div class=\"span1\"><i class=\"icon-warning-sign\"></i></div>\n                <div class=\"span11\">\n                    <div class=\"beneficiaryName\">";
  foundHelper = helpers.beneficiaryName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.beneficiaryName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                    <div class=\"dueDate\">";
  foundHelper = helpers.dueDate;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.dueDate; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                    <div class=\"detail\">";
  foundHelper = helpers.description;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.description; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand in-navbar ellipsis affected-by-sidepanel\" href=\"#\">\n            <span class=\"navbar-brand-icon-holder affected-by-sidepanel\"><i class=\"icon-book icon-white navbar-brand-icon\"></i></span>\n            <span class=\"name\">Workplan: ";
  foundHelper = helpers.village;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.village; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span>\n        </a>\n    </div>\n</div>\n\n<div id=\"content\" class=\"content  affected-by-sidepanel\">\n    <h3 class=\"priority-header\">Soon</h3>\n    ";
  stack1 = depth0.alerts;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    <div class=\"alert low-priority\">\n            <div class=\"row-fluid\">\n                <div class=\"span1\"><i class=\"icon-warning-sign\"></i></div>\n                <div class=\"span11\">\n                    <div class=\"beneficiaryName\">Akarishma</div>\n                    <div class=\"villageName\">Bherya</div>\n                    <div class=\"detail\">Lorem ipsum dolor sit amet</div>\n                </div>\n            </div>\n    </div>\n</div>\n";
  return buffer;});
})();