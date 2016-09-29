	<%@ page import="model.user.iUser" %>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html"><h1>
						<img src="images/logo.png" alt="" />
					</h1></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="top-search">
					<form class="navbar-form navbar-right">
						<input type="text" class="form-control" placeholder="Search...">
						<input type="submit" value=" ">
					</form>
				</div>
				<div class="header-top-right">
					<div class="file">
						<a href="upload.html">Upload</a>
					</div>
					<div class="signin">
						<a href="#small-dialog3" class="play-icon popup-with-zoom-anim">Sign
							Up</a>
						<!-- pop-up-box -->
						<script type="text/javascript" src="js/modernizr.custom.min.js"></script>
						<link href="css/popuo-box.css" rel="stylesheet" type="text/css"
							media="all" />
						<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
						<!--//pop-up-box -->

						<div id="small-dialog3" class="mfp-hide">
							<h3>Create Account</h3>
							<div class="social-sits">
								<div class="facebook-button">
									<a href="#">Connect with Facebook</a>
								</div>
								<div class="chrome-button">
									<a href="#">Connect with Google</a>
								</div>
								<div class="button-bottom">
									<p>
										Already have an account? <a href="#small-dialog"
											class="play-icon popup-with-zoom-anim">Login</a>
									</p>
								</div>
							</div>
							<div class="signup">
								<form role="form" method="POST" action="./Register">
									<input name="email" type="email" class="email"
										placeholder="Email" maxlength="30" required="required"
										pattern=".{<%=iUser.MIN_EMAIL_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										<%-- title="<%=iUser.MIN_EMAIL_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters" /> --%>
										title="Enter a valid email"/>
									<input name="password" type="password" placeholder="Password"
										maxlength="30" required="required"
										pattern=".{<%=iUser.MIN_PASSWORD_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										title="<%=iUser.MIN_PASSWORD_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters"
										autocomplete="off" /> <input name="user_name" type="text"
										class="email" placeholder="Username" maxlength="30"
										required="required"
										pattern=".{<%=iUser.MIN_NAME_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										title="<%=iUser.MIN_NAME_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters" />
									<input name="first_name" type="text" class="email"
										placeholder="First name" maxlength="30" required="required"
										pattern=".{<%=iUser.MIN_NAME_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										title="<%=iUser.MIN_NAME_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters" />
									<input name="last_name" type="text" class="email"
										placeholder="Last name" maxlength="30" required="required"
										pattern=".{<%=iUser.MIN_NAME_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										title="<%=iUser.MIN_NAME_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters" />
									<input type="submit" value="Sign Up" />
								</form>
							</div>
							<div class="clearfix"></div>
						</div>
						<div id="small-dialog7" class="mfp-hide">
							<h3>Create Account</h3>
							<div class="social-sits">
								<div class="facebook-button">
									<a href="#">Connect with Facebook</a>
								</div>
								<div class="chrome-button">
									<a href="#">Connect with Google</a>
								</div>
								<div class="button-bottom">
									<p>
										Already have an account? <a href="#small-dialog"
											class="play-icon popup-with-zoom-anim">Login</a>
									</p>
								</div>
							</div>
							<div class="signup">
								<form action="upload.html">
									<input type="text" class="email" placeholder="Email"
										required="required"
										pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?"
										title="Enter a valid email" /> <input type="password"
										placeholder="Password" required="required" pattern=".{6,}"
										title="Minimum 6 characters required" autocomplete="off" /> <input
										type="submit" value="Sign In" />
								</form>
							</div>
							<div class="clearfix"></div>
						</div>
						<div id="small-dialog4" class="mfp-hide">
							<h3>Feedback</h3>
							<div class="feedback-grids">
								<div class="feedback-grid">
									<p>Suspendisse tristique magna ut urna pellentesque, ut
										egestas velit faucibus. Nullam mattis lectus ullamcorper dui
										dignissim, sit amet egestas orci ullamcorper.</p>
								</div>
								<div class="button-bottom">
									<p>
										<a href="#small-dialog" class="play-icon popup-with-zoom-anim">Sign
											in</a> to get started.
									</p>
								</div>
							</div>
						</div>
						<div id="small-dialog5" class="mfp-hide">
							<h3>Help</h3>
							<div class="help-grid">
								<p>Suspendisse tristique magna ut urna pellentesque, ut
									egestas velit faucibus. Nullam mattis lectus ullamcorper dui
									dignissim, sit amet egestas orci ullamcorper.</p>
							</div>
							<div class="help-grids">
								<div class="help-button-bottom">
									<p>
										<a href="#small-dialog4"
											class="play-icon popup-with-zoom-anim">Feedback</a>
									</p>
								</div>
								<div class="help-button-bottom">
									<p>
										<a href="#small-dialog6"
											class="play-icon popup-with-zoom-anim">Lorem ipsum dolor
											sit amet</a>
									</p>
								</div>
								<div class="help-button-bottom">
									<p>
										<a href="#small-dialog6"
											class="play-icon popup-with-zoom-anim">Nunc vitae rutrum
											enim</a>
									</p>
								</div>
								<div class="help-button-bottom">
									<p>
										<a href="#small-dialog6"
											class="play-icon popup-with-zoom-anim">Mauris at volutpat
											leo</a>
									</p>
								</div>
								<div class="help-button-bottom">
									<p>
										<a href="#small-dialog6"
											class="play-icon popup-with-zoom-anim">Mauris vehicula
											rutrum velit</a>
									</p>
								</div>
								<div class="help-button-bottom">
									<p>
										<a href="#small-dialog6"
											class="play-icon popup-with-zoom-anim">Aliquam eget ante
											non orci fac</a>
									</p>
								</div>
							</div>
						</div>
						<div id="small-dialog6" class="mfp-hide">
							<div class="video-information-text">
								<h4>Video information & settings</h4>
								<p>Suspendisse tristique magna ut urna pellentesque, ut
									egestas velit faucibus. Nullam mattis lectus ullamcorper dui
									dignissim, sit amet egestas orci ullamcorper.</p>
								<ol>
									<li>Nunc vitae rutrum enim. Mauris at volutpat leo.
										Vivamus dapibus mi ut elit fermentum tincidunt.</li>
									<li>Nunc vitae rutrum enim. Mauris at volutpat leo.
										Vivamus dapibus mi ut elit fermentum tincidunt.</li>
									<li>Nunc vitae rutrum enim. Mauris at volutpat leo.
										Vivamus dapibus mi ut elit fermentum tincidunt.</li>
									<li>Nunc vitae rutrum enim. Mauris at volutpat leo.
										Vivamus dapibus mi ut elit fermentum tincidunt.</li>
									<li>Nunc vitae rutrum enim. Mauris at volutpat leo.
										Vivamus dapibus mi ut elit fermentum tincidunt.</li>
								</ol>
							</div>
						</div>
						<script>
							$(document).ready(function() {
								$('.popup-with-zoom-anim').magnificPopup({
									type : 'inline',
									fixedContentPos : false,
									fixedBgPos : true,
									overflowY : 'auto',
									closeBtnInside : true,
									preloader : false,
									midClick : true,
									removalDelay : 300,
									mainClass : 'my-mfp-zoom-in'
								});

							});
						</script>
					</div>
					<div class="signin">
						<a href="#small-dialog" class="play-icon popup-with-zoom-anim">Sign
							In</a>
						<div id="small-dialog" class="mfp-hide">
							<h3>Login</h3>
 							<div class="social-sits">
								<div class="facebook-button">
									<a href="#">Connect with Facebook</a>
								</div>
								<div class="chrome-button">
									<a href="#">Connect with Google</a>
								</div>
								<div class="button-bottom">
									<p>
										New account? <a href="#small-dialog3"
											class="play-icon popup-with-zoom-anim">Signup</a>
									</p>
								</div>
							</div> 
							<div class="signup">
								<form action="./Login">
									<input name="email" type="email" class="email"
										placeholder="Enter email" required="required"
										pattern=".{<%=iUser.MIN_EMAIL_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										title="<%=iUser.MIN_EMAIL_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters" />
									<input name="password" type="password" placeholder="Password"
										required="required"
										pattern=".{<%=iUser.MIN_PASSWORD_LENGTH%>,<%=iUser.MAX_FIELD_LENGTH%>}"
										required
										title="<%=iUser.MIN_PASSWORD_LENGTH%> to <%=iUser.MAX_FIELD_LENGTH%> characters"
										autocomplete="off" /> <input type="submit" value="LOGIN" />
								</form>
								<div class="forgot">
									<a href="#">Forgot password ?</a>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</nav>