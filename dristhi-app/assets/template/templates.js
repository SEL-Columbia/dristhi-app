(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['anc_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n            ";
  stack1 = depth0.isCompleted;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.programWithDepth(program4, data, depth1),fn:self.programWithDepth(program2, data, depth1)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  return buffer;}
function program2(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo completed\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box checked\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program4(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo overdue\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program6(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n            ";
  stack1 = depth0.isCompleted;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.programWithDepth(program9, data, depth1),fn:self.programWithDepth(program7, data, depth1)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  return buffer;}
function program7(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo completed\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box checked\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program9(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo upcoming\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program11(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                <div class=\"text-center big-text high-risk\">\n                                    <i class=\"icon-circle-arrow-up\" style=\"margin-top: 3px;\"></i> High Risk</span>\n                                </div>\n                                <div class=\"beneficiary-detail-footer risk-detail-text\">";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.riskDetail;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                            ";
  return buffer;}

function program13(depth0,data) {
  
  
  return "\n                                <div class=\"text-center big-text normal-risk\">Normal Risk</div>\n                            ";}

function program15(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                                <div class=\"container-section-component row-fluid\">\n                                        <div class=\"timeline dl\">\n                                            <div class=\"span2 ";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dt\">\n                                                <i class=\"icon-eye-open\"></i>\n                                            </div>\n                                            <div class=\"span6 details-";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dd\">\n                                                <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(16, program16, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                            </div>\n                                            <div class=\"span4 pull-right text-right details-";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dd\">\n                                                ";
  foundHelper = helpers.date;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.date; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                                            </div>\n                                        </div>\n                                    </div>\n                            ";
  return buffer;}
function program16(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                                ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "<br>\n                                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <ul class=\"nav pull-left\">\n            <img class=\"sidepanel-icon affected-by-sidepanel\"><img class=\"separator\">\n            <span class=\"name\">ANC</span>\n        </ul>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical in-navbar\"></li>\n            <li data-modal-target=\"#ancFormModal\"><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"ancFormModal\" class=\"modal-content\">\n    <div class=\"modal-row\" data-form=\"ANC_SERVICES\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">ANC visit</div>\n    <div class=\"divider\"></div>\n    <div class=\"modal-row\" data-form=\"ANC_CLOSE\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">Close ANC record</div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div id=\"ec-name\" class=\"span10\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
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
  buffer += escapeExpression(stack1) + "</li>\n                    <li class=\"light-text\">TC: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n\n    <div id=\"todos\" class=\"registry-container container-fluid\">\n        ";
  stack1 = depth0.urgentTodos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program1, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  stack1 = depth0.todos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program6, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            ANC Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                            ";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(13, program13, data),fn:self.program(11, program11, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                    <div class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-content\">\n                            <div class=\"span6 text-center\">\n                                <span class=\"big-text-numbers\">";
  stack1 = depth0.pregnancyDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.monthsPregnant;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Months Pregnant</span>\n                            </div>\n                            <div class=\"span6 text-center\">\n                                <span class=\"big-text-numbers\">";
  stack1 = depth0.pregnancyDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.edd;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">EDD</span>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            Delivery Plan\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding container-section\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"span1\"><!-- <i class=\"icon-check\"></i> -->\n                        <label class=\"checkbox\">\n                            <input type=\"checkbox\">\n                        </label>\n                    </div>\n\n                    <div class=\"span3\">Facility</div>\n                    <div class=\"span8\">";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.deliveryPlace;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"span1\">\n                        <label class=\"checkbox\">\n                            <input type=\"checkbox\">\n                        </label>\n                    </div>\n                    <div class=\"span3\">Contact</div>\n                    <div class=\"span8\">";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.ashaPhoneNumber;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"span1\">\n                        <label class=\"checkbox\">\n                            <input type=\"checkbox\">\n                        </label>\n                    </div>\n                    <div class=\"span3\">ASHA</div>\n                    <div class=\"span8\">";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.ashaName;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            Timeline\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                        <div class=\"container-section container-fluid\">\n                            ";
  stack1 = depth0.timelineEvents;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(15, program15, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
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
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span8\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">\n                        ";
  foundHelper = helpers.womanName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.womanName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                        ";
  stack1 = depth0.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </li>\n                    <li>";
  foundHelper = helpers.husbandName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.husbandName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>\n                     Thaayi #: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                    </li>\n                </ul>\n            </div>\n\n            <div class=\"span4 pull-text-completely-right\">\n                ";
  foundHelper = helpers.villageName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.villageName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                <p>\n				";
  stack1 = depth0.hasTodos;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(4, program4, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n			</div>\n\n            <div class=\"divider divider-padding-both\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return "<span class=\"high-risk\"> HRP </span>";}

function program4(depth0,data) {
  
  
  return "\n				<div class = \"todo pull-text-completely-right\">\n					<img src=\"../img/icons/icon-hastodo.png\"> </img>\n               	</div>\n				";}

  stack1 = helpers.each.call(depth0, depth0, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['ec_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n            ";
  stack1 = depth0.isCompleted;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.programWithDepth(program4, data, depth1),fn:self.programWithDepth(program2, data, depth1)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  return buffer;}
function program2(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo completed\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box checked\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program4(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo overdue\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program6(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n            ";
  stack1 = depth0.isCompleted;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.programWithDepth(program9, data, depth1),fn:self.programWithDepth(program7, data, depth1)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  return buffer;}
function program7(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo completed\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box checked\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program9(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo upcoming\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program11(depth0,data) {
  
  
  return "\n                        <div class=\"row-fluid unavailable-data\">\n                            No child records available\n                        </div>\n                    ";}

function program13(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                        <div class=\"beneficiary-detail-content\">\n                        ";
  stack1 = depth0.children;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(14, program14, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                        </div>\n                    ";
  return buffer;}
function program14(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            ";
  stack1 = depth0.isFemale;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(17, program17, data),fn:self.program(15, program15, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                        ";
  return buffer;}
function program15(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                                <div class=\"ec-child girl\">\n                                    <div style=\"text-align: center;\">\n                                        <span style=\"font-weight: bold\">";
  foundHelper = helpers.age;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.age; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br>\n                                        Girl\n                                    </div>\n                                </div>\n                            ";
  return buffer;}

function program17(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                                <div class=\"ec-child boy\">\n                                    <div style=\"text-align: center;\">\n                                        <span style=\"font-weight: bold;\">";
  foundHelper = helpers.age;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.age; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</span><br>\n                                        Boy\n                                    </div>\n                                </div>\n                            ";
  return buffer;}

function program19(depth0,data) {
  
  
  return "\n                    <div class=\"row-fluid unavailable-data\">\n                        No timeline data available\n                    </div>\n                ";}

function program21(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                    <div class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-content\">\n                            <div class=\"container-section container-fluid\">\n                                ";
  stack1 = depth0.timelineEvents;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(22, program22, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </div>\n                        </div>\n                    </div>\n                ";
  return buffer;}
function program22(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                                    <div class=\"container-section-component row-fluid\">\n                                            <div class=\"timeline dl\">\n                                                <div class=\"span2 ";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dt\">\n                                                    <i class=\"icon-eye-open\"></i>\n                                                </div>\n                                                <div class=\"span6 details-";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dd\">\n                                                    <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                    ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(23, program23, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                                </div>\n                                                <div class=\"span4 pull-right text-right details-";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dd\">\n                                                    ";
  foundHelper = helpers.date;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.date; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                                                </div>\n                                            </div>\n                                        </div>\n                                ";
  return buffer;}
function program23(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                                    ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "<br>\n                                                    ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <ul class=\"nav pull-left\">\n            <img class=\"sidepanel-icon affected-by-sidepanel\"><img class=\"separator\">\n            <span class=\"name\">EC</span>\n        </ul>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical in-navbar\"></li>\n            <li data-modal-target=\"#ecFormModal\"><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"ecFormModal\" class=\"modal-content\">\n    <div class=\"modal-row\" data-form=\"EC_FP_UPDATE\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">Family planning update</div>\n    <div class=\"divider\"></div>\n    <div class=\"modal-row\" data-form=\"EC_FP_COMPLICATIONS\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">FP complications</div>\n    <div class=\"divider\"></div>\n    <div class=\"modal-row\" data-form=\"EC_REGISTER_ANC\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">Register as ANC</div>\n    <div class=\"divider\"></div>\n    <div class=\"modal-row\" data-form=\"EC_CLOSE\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">Close EC record</div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div id=\"ec-name\" class=\"span12\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
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
  buffer += escapeExpression(stack1) + "</li>\n                    <li class=\"light-text\">EC: ";
  foundHelper = helpers.ecNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.ecNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n        </div>\n    </div>\n\n    <div id=\"todos\" class=\"registry-container container-fluid\">\n        ";
  stack1 = depth0.urgentTodos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program1, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  stack1 = depth0.todos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program6, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            EC Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Family Planning</div>\n                    <div class=\"beneficiary-detail-content\">\n                        <i class=\"icon-ok\"></i><span class=\"family-planning-current\">";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.currentMethod;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br/>\n                        <a class=\"btn family-planning-button btn-large\" href=\"family_planning.html\">Family Planning Tool</a>\n                    </div>\n                    <div class=\"divider divider-padding-top\"></div>\n                </div>\n\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-header\">Children</div>\n                    ";
  stack1 = depth0.children;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.length;
  foundHelper = helpers.ifequal;
  stack1 = foundHelper ? foundHelper.call(depth0, stack1, 0, {hash:{},inverse:self.program(13, program13, data),fn:self.program(11, program11, data)}) : helperMissing.call(depth0, "ifequal", stack1, 0, {hash:{},inverse:self.program(13, program13, data),fn:self.program(11, program11, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                </div>\n            </div>\n        </div>\n    </div>\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            Timeline\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                ";
  stack1 = depth0.timelineEvents;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.length;
  foundHelper = helpers.ifequal;
  stack1 = foundHelper ? foundHelper.call(depth0, stack1, 0, {hash:{},inverse:self.program(21, program21, data),fn:self.program(19, program19, data)}) : helperMissing.call(depth0, "ifequal", stack1, 0, {hash:{},inverse:self.program(21, program21, data),fn:self.program(19, program19, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n        </div>\n    </div>\n</div>\n";
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
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span8\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">\n                        ";
  foundHelper = helpers.wifeName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.wifeName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                        ";
  stack1 = depth0.isHighPriority;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </li>\n                    <li>";
  foundHelper = helpers.husbandName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.husbandName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>\n                     EC #: ";
  foundHelper = helpers.ecNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.ecNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                    </li>\n                </ul>\n            </div>\n\n            <div class=\"span4 pull-text-completely-right\">\n                ";
  foundHelper = helpers.villageName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.villageName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                <p>\n                ";
  stack1 = depth0.hasTodos;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(4, program4, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-both\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return "<span class=\"high-risk\"> HP </span>";}

function program4(depth0,data) {
  
  
  return "\n                <div class = \"todo pull-text-completely-right\">\n                    <img src=\"../img/icons/icon-hastodo.png\"></img>\n                </div>\n                ";}

  stack1 = helpers.each.call(depth0, depth0, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['pnc_detail'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n            ";
  stack1 = depth0.isCompleted;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.programWithDepth(program4, data, depth1),fn:self.programWithDepth(program2, data, depth1)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  return buffer;}
function program2(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo completed\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box checked\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program4(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo overdue\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program6(depth0,data,depth1) {
  
  var buffer = "", stack1;
  buffer += "\n            ";
  stack1 = depth0.isCompleted;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.programWithDepth(program9, data, depth1),fn:self.programWithDepth(program7, data, depth1)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  return buffer;}
function program7(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo completed\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box checked\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program9(depth0,data,depth2) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                <div class=\"alert alert-todo upcoming\" data-form=\"";
  foundHelper = helpers.formToOpen;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.formToOpen; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\" data-caseid=\"";
  stack1 = depth2.caseId;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "\" data-visitcode=\"";
  foundHelper = helpers.visitCode;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.visitCode; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">\n                    <div class=\"row-fluid\">\n                        <div class=\"span2\">\n                            <img class=\"todo-box\"/>\n                        </div>\n                        <div class=\"span10 alert-todo-message\">\n                            <div class=\"detail\">";
  foundHelper = helpers.message;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.message; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        </div>\n                    </div>\n                </div>\n            ";
  return buffer;}

function program11(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                                <div class=\"text-center big-text high-risk\">\n                                    <i class=\"icon-circle-arrow-up\" style=\"margin-top: 3px;\"></i> High Risk</span>\n                                </div>\n                                <div class=\"beneficiary-detail-footer risk-detail-text\">";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.riskDetail;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</div>\n                            ";
  return buffer;}

function program13(depth0,data) {
  
  
  return "\n                                <div class=\"text-center big-text normal-risk\">Normal Risk</div>\n                            ";}

function program15(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                            <ol>\n                                  ";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.deliveryComplications;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(16, program16, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                            </ol>\n                            ";
  return buffer;}
function program16(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                    <li>\n                                    ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "\n                                    </li>\n                                  ";
  return buffer;}

function program18(depth0,data) {
  
  
  return "\n                                <div class=\"unavailable-data\">\n                                    No delivery complications known.\n                                </div>\n                            ";}

function program20(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n                                <div class=\"container-section-component row-fluid\">\n                                        <div class=\"timeline dl\">\n                                            <div class=\"span2 ";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dt\">\n                                                <i class=\"icon-eye-open\"></i>\n                                            </div>\n                                            <div class=\"span6 details-";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dd\">\n                                                <strong>";
  foundHelper = helpers.title;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.title; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</strong><br>\n                                                ";
  stack1 = depth0.details;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(21, program21, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                                            </div>\n                                            <div class=\"span4 pull-right text-right details-";
  stack1 = depth0.status;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + " dd\">\n                                                ";
  foundHelper = helpers.date;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.date; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                                            </div>\n                                        </div>\n                                    </div>\n                            ";
  return buffer;}
function program21(depth0,data) {
  
  var buffer = "";
  buffer += "\n                                                ";
  depth0 = typeof depth0 === functionType ? depth0() : depth0;
  buffer += escapeExpression(depth0) + "<br>\n                                                ";
  return buffer;}

  buffer += "<div class=\"navbar navbar-fixed-top affected-by-sidepanel\">\n    <div class=\"navbar-inner\">\n        <ul class=\"nav pull-left\">\n            <img class=\"sidepanel-icon affected-by-sidepanel\"><img class=\"separator\">\n            <span class=\"name\">PNC</span>\n        </ul>\n\n        <ul class=\"nav pull-right affected-by-sidepanel\">\n            <li class=\"divider-vertical in-navbar\"></li>\n            <li data-modal-target=\"#pncFormModal\"><span class=\"navbar-text\" href=\"#\"><i class=\"icon-plus icon-white\"></i> UPDATE</span></li>\n        </ul>\n    </div>\n</div>\n\n<div id=\"pncFormModal\" class=\"modal-content\">\n    <div class=\"modal-row\" data-form=\"PNC_SERVICES\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">PNC visit</div>\n    <div class=\"divider\"></div>\n    <div class=\"modal-row\" data-form=\"CHILD_IMMUNIZATION\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">Child immunization</div>\n    <div class=\"divider\"></div>\n    <div class=\"modal-row\" data-form=\"PNC_CLOSE\" data-caseid=\"";
  foundHelper = helpers.caseId;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.caseId; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\">Close PNC record</div>\n</div>\n\n<div id=\"content\" class=\"content affected-by-sidepanel\">\n    <div class=\"registry-container container-fluid\">\n        <div class=\"row-fluid\">\n            <div id=\"ec-name\" class=\"span10\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">";
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
  buffer += escapeExpression(stack1) + "</li>\n                </ul>\n            </div>\n\n            <div class=\"span2 pull-right\">\n                <a class=\"ec-information-button btn btn-inverse\" href=\"#\">\n                    <i class=\"icon-user icon-white\"></i>\n                    <i class=\"icon-align-justify icon-white\"></i>\n                </a>\n            </div>\n        </div>\n    </div>\n\n    <div id=\"todos\" class=\"registry-container container-fluid\">\n        ";
  stack1 = depth0.urgentTodos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program1, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n        ";
  stack1 = depth0.todos;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.programWithDepth(program6, data, depth0)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n\n        <div class=\"divider\"></div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            Summary\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                            ";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(13, program13, data),fn:self.program(11, program11, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </div>\n                    <div class=\"row-fluid beneficiary-detail-component\">\n                        <div class=\"beneficiary-detail-content\">\n                            <div class=\"span6 text-center\">\n                                <span class=\"big-text\">";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.daysPostpartum;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Days Postpartum</span>\n                            </div>\n                            <div class=\"span6 text-center\">\n                                <span class=\"big-text\">";
  stack1 = depth0.pncDetails;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.dateOfDelivery;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span><br><span class=\"meta-summary-text\">Date of Delivery</span>\n                            </div>\n                        </div>\n                        <div class=\"divider\"></div>\n                        <div class=\"row-fluid beneficiary-detail-component\">\n                            <b> Delivery Complications </b>\n                            ";
  stack1 = depth0.details;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.deliveryComplications;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.program(18, program18, data),fn:self.program(15, program15, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            PNC Plan\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-section container-fluid\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    42 day checklist\n                </div>\n            </div>\n        </div>\n    </div>\n\n    <div class=\"registry-container container-fluid\">\n        <div class=\"big-text row-fluid detail-section-header\">\n            Timeline\n        </div>\n        <div class=\"well well-for-beneficiary-details\">\n            <div class=\"container-fluid container-no-padding\">\n                <div class=\"row-fluid beneficiary-detail-component\">\n                    <div class=\"beneficiary-detail-content\">\n                        <div class=\"container-section container-fluid\">\n                            ";
  stack1 = depth0.timelineEvents;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(20, program20, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>\n";
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
  buffer += escapeExpression(stack1) + " class=\"client-list-item\" onClick=\"javascript: return true;\">\n            <div class=\"span8\">\n                <ul class=\"client-details unstyled\">\n                    <li class=\"big-text\">\n                        ";
  foundHelper = helpers.womanName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.womanName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                        ";
  stack1 = depth0.isHighRisk;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(2, program2, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n                    </li>\n                    <li>";
  foundHelper = helpers.husbandName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.husbandName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</li>\n                    <li>\n                     Thaayi #: ";
  foundHelper = helpers.thaayiCardNumber;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.thaayiCardNumber; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                    </li>\n                </ul>\n            </div>\n\n            <div class=\"span4 pull-text-completely-right\">\n                ";
  foundHelper = helpers.villageName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.villageName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "\n                <p>\n                ";
  stack1 = depth0.hasTodos;
  stack1 = helpers['if'].call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(4, program4, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </div>\n\n            <div class=\"divider divider-padding-both\"></div>\n        </a>\n    </div>\n";
  return buffer;}
function program2(depth0,data) {
  
  
  return "<span class=\"high-risk\"> HRP </span>";}

function program4(depth0,data) {
  
  
  return "\n                <div class = \"todo pull-text-completely-right\">\n                    <img src=\"../img/icons/icon-hastodo.png\"> </img>\n                </div>\n                ";}

  stack1 = helpers.each.call(depth0, depth0, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n";
  return buffer;});
templates['sidepanel'] = template(function (Handlebars,depth0,helpers,partials,data) {
  helpers = helpers || Handlebars.helpers;
  var buffer = "", stack1, foundHelper, functionType="function", escapeExpression=this.escapeExpression;


  buffer += "<div id=\"sidepanel\" class=\"sidepanel affected-by-sidepanel\">\n    <div class=\"menu-background\">\n        <div class=\"navbar navbar-fixed-top navbar-sidepanel\">\n            <div class=\"navbar-inner nav-sidepanel\">\n                <span class=\"brand in-navbar in-sidepanel\" href=\"#\"><img src=\"../img/icons/nav-logo.png\"></span>\n\n                <ul class=\"nav pull-right\">\n                    <li><img class=\"progress-indicator hidden\" src=\"../img/progress.gif\"></img></li>\n                </ul>\n            </div>\n        </div>\n\n        <div class=\"content sidepanel-content\">\n            <div class=\"menu-button-container container-fluid\">\n                <div class=\"row-fluid\">\n                    <div class=\"menu-button span6\" id=\"workplanButton\">\n                        <img src=\"../img/icons/nav-workplan.png\" class=\"menu-icon\"><br>\n                        Workplan\n                    </div>\n                    <div class=\"menu-button span6\" id=\"reportsButton\">\n                        <img src=\"../img/icons/nav-reporting.png\" class=\"menu-icon\"><br>\n                        Reporting\n                    </div>\n                </div>\n                <div class=\"menu-option row-fluid\" id=\"eligibleCoupleMenuOption\">\n                    <div class=\"span1\">\n                        <img src=\"../img/icons/nav-ec.png\">\n                    </div>\n                    <div class=\"span9\">Eligible Couple</div>\n                    <div class=\"span2 pull-text-completely-right\">";
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
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n            <div class=\"alert overdue\">\n                <div class=\"row-fluid\">\n                    <div class=\"span2\">\n                        <img class=\"todo-box\"/>\n                    </div>\n                    <div class=\"span10\">\n                        <div class=\"beneficiaryName\">";
  foundHelper = helpers.beneficiaryName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.beneficiaryName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        <div class=\"detail\">";
  foundHelper = helpers.description;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.description; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " by ";
  foundHelper = helpers.dueDate;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.dueDate; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                    </div>\n                </div>\n            </div>\n        ";
  return buffer;}

function program3(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n            <div class=\"alert upcoming\">\n                <div class=\"row-fluid\">\n                    <div class=\"span2\">\n                        <img class=\"todo-box\"/>\n                    </div>\n                    <div class=\"span10\">\n                        <div class=\"beneficiaryName\">";
  foundHelper = helpers.beneficiaryName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.beneficiaryName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        <div class=\"detail\">";
  foundHelper = helpers.description;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.description; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " by ";
  foundHelper = helpers.dueDate;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.dueDate; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                    </div>\n                </div>\n            </div>\n        ";
  return buffer;}

function program5(depth0,data) {
  
  var buffer = "", stack1, foundHelper;
  buffer += "\n            <div class=\"alert completed\">\n                <div class=\"row-fluid\">\n                    <div class=\"span2\">\n                        <img class=\"todo-box checked\"/>\n                    </div>\n                    <div class=\"span10\">\n                        <div class=\"beneficiaryName\">";
  foundHelper = helpers.beneficiaryName;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.beneficiaryName; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                        <div class=\"detail\">";
  foundHelper = helpers.description;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.description; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + " by ";
  foundHelper = helpers.dueDate;
  if (foundHelper) { stack1 = foundHelper.call(depth0, {hash:{}}); }
  else { stack1 = depth0.dueDate; stack1 = typeof stack1 === functionType ? stack1() : stack1; }
  buffer += escapeExpression(stack1) + "</div>\n                    </div>\n                </div>\n            </div>\n        ";
  return buffer;}

  buffer += "<div class=\"tabbable\">\n    <ul class=\"nav nav-tabs navbar-fixed-top affected-by-sidepanel\">\n        <li class=\"active overdue\"><a href=\"#overdue\" data-toggle=\"tab\">Overdue (<span id=\"overdue-count\">";
  stack1 = depth0.overdue;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.length;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span>)</a></li>\n        <li class=\"upcoming\"><a href=\"#upcoming\" data-toggle=\"tab\">To Do (<span id=\"upcoming-count\">";
  stack1 = depth0.upcoming;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.length;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span>)</a></li>\n        <li class=\"completed\"><a href=\"#completed\" data-toggle=\"tab\">Done (<span id=\"completed-count\">";
  stack1 = depth0.completed;
  stack1 = stack1 == null || stack1 === false ? stack1 : stack1.length;
  stack1 = typeof stack1 === functionType ? stack1() : stack1;
  buffer += escapeExpression(stack1) + "</span>)</a></li>\n    </ul>\n</div>\n\n<div class=\"tab-content container-fluid container-no-padding\">\n    <div class=\"tab-pane overdue active\" id=\"overdue\">\n        ";
  stack1 = depth0.overdue;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(1, program1, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n\n    <div class=\"tab-pane upcoming\" id=\"upcoming\">\n        ";
  stack1 = depth0.upcoming;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(3, program3, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n\n    <div class=\"tab-pane completed\" id=\"completed\">\n        ";
  stack1 = depth0.completed;
  stack1 = helpers.each.call(depth0, stack1, {hash:{},inverse:self.noop,fn:self.program(5, program5, data)});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n    </div>\n</div>\n";
  return buffer;});
})();