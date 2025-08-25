import * as React from 'react';
import {
  FormControl,
  FormHelperText,
  IconButton,
  InputAdornment,
  InputLabel,
  OutlinedInput,
  TextField
} from '@mui/material'
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

const InputCustom = ({
  id = 'outlined-basic',
  label = 'Outlined',
  variant = 'outlined',
  placeholder = '',
  fullWidth = true,
  size = 'small',
  color = 'primary',
  error = false,
  required = false,
  margin = 'dense',
  isPassword = false,
  isOTP = false,
  slotProps = {},
  value,
  onChange,
}) => {

  const [showPassword, setShowPassword] = React.useState(false);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const handleMouseUpPassword = (event) => {
    event.preventDefault();
  };


  return (
    <div>
      {isPassword
        ? <FormControl id={id} variant={variant} fullWidth={fullWidth} size={size} color={color} error={error} required={required} margin={margin}>
          <InputLabel htmlFor="outlined-adornment-password">{label}</InputLabel>
          <OutlinedInput
            id="outlined-adornment-password"
            type={showPassword ? 'text' : 'password'}
            placeholder={placeholder}
            value={value}
            onChange={onChange}
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label={
                    showPassword ? 'hide the password' : 'display the password'
                  }
                  onClick={handleClickShowPassword}
                  onMouseDown={handleMouseDownPassword}
                  onMouseUp={handleMouseUpPassword}
                  edge="end"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            }
            label="Password"
          />
          </FormControl>
        : <TextField id={id} label={label} variant={variant} placeholder={placeholder} fullWidth={fullWidth} size={size} color={color} error={error} required={required} margin={margin} value={value} onChange={onChange} slotProps={{
          input: {
            ...(isOTP && {
              inputMode: 'numeric',
              pattern: '[0-9]*',
              maxLength: 6,
              onKeyDown: (e) => {
                if (!/[0-9]/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Delete') {
                  e.preventDefault();
                }
              },
            }),
            ...slotProps.input,
          },
        }}/>
      }
    </div>
  )
}

export default InputCustom