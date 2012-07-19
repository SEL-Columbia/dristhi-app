(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
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
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span3 high-risk\">\n                    ";
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
  foundHelper = helpers.event;
  stack1 = foundHelper || depth0.event;
  if(typeof stack1 === functionType) { stack1 = stack1.call(depth0, { hash: {} }); }
  else if(stack1=== undef) { stack1 = helperMissing.call(depth0, "event", { hash: {} }); }
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

  buffer += "<div class=\"navbar navbar-fixed-top\">\n    <div class=\"navbar-inner\">\n        <a class=\"brand list\" href=\"#\">EC</a>\n\n        ";
  foundHelper = helpers.isHighPriority;
  stack1 = foundHelper || depth0.isHighPriority;
  stack2 = helpers['if'];
  tmp1 = self.program(1, program1, data);
  tmp1.hash = {};
  tmp1.fn = tmp1;
  tmp1.inverse = self.noop;
  stack1 = stack2.call(depth0, stack1, tmp1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n</div>\n\n<div class=\"content\">\n    <div class=\"ec-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div class=\"span4\">\n                <img src=\"../img/matt.png\"/>\n            </div>\n\n            <div id=\"ec-name\" class=\"span6\">\n                <ul class=\"ec-details unstyled\">\n                    <li class=\"ec-name\">";
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
})();