import re

# Regex patterns
# --------------------
# Regex pattern for a style attribute image url
style_image_url_pattern = re.compile(r"""url\(["|']+(.*)["|']+\)""")
