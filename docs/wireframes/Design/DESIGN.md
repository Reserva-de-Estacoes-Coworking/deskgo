---
name: Warm Welcomes
colors:
  surface: '#faf9fc'
  surface-dim: '#dad9dd'
  surface-bright: '#faf9fc'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f4f3f7'
  surface-container: '#eeedf1'
  surface-container-high: '#e9e7eb'
  surface-container-highest: '#e3e2e6'
  on-surface: '#1a1c1e'
  on-surface-variant: '#43474e'
  inverse-surface: '#2f3033'
  inverse-on-surface: '#f1f0f4'
  outline: '#74777f'
  outline-variant: '#c4c6cf'
  surface-tint: '#455f87'
  primary: '#022448'
  on-primary: '#ffffff'
  primary-container: '#1e3a5f'
  on-primary-container: '#8aa4cf'
  inverse-primary: '#adc8f5'
  secondary: '#7f5534'
  on-secondary: '#ffffff'
  secondary-container: '#ffc69c'
  on-secondary-container: '#7a502f'
  tertiary: '#002b07'
  on-tertiary: '#ffffff'
  tertiary-container: '#0a4313'
  on-tertiary-container: '#78b174'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d5e3ff'
  primary-fixed-dim: '#adc8f5'
  on-primary-fixed: '#001c3b'
  on-primary-fixed-variant: '#2d486d'
  secondary-fixed: '#ffdcc4'
  secondary-fixed-dim: '#f3bb92'
  on-secondary-fixed: '#2f1400'
  on-secondary-fixed-variant: '#643e1f'
  tertiary-fixed: '#b6f2af'
  tertiary-fixed-dim: '#9ad595'
  on-tertiary-fixed: '#002204'
  on-tertiary-fixed-variant: '#1b5120'
  background: '#faf9fc'
  on-background: '#1a1c1e'
  surface-variant: '#e3e2e6'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 48px
    fontWeight: '800'
    lineHeight: 56px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.01em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.01em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 8px
  container-padding-mobile: 1.25rem
  container-padding-desktop: 2.5rem
  gutter: 1.5rem
  section-gap: 4rem
---

## Brand & Style

This design system is built for the intersection of hospitality and productivity, pivoting away from cold, industrial efficiency toward a "fun and friendly" experience. The brand personality is welcoming, approachable, and optimistic, aiming to make task management and guest coordination feel less like a chore and more like a shared success.

The design style blends **Modern Corporate** reliability with **Soft Minimalism** and **Tactile** accents. We prioritize high-legibility typography, generous whitespace to reduce cognitive load, and soft, approachable geometry. Playful patterns—such as subtle organic shapes or dotted grids in the background—should be used to break up large monochromatic surfaces and inject a sense of movement and friendliness.

## Colors

The palette is grounded in professional Navy and natural Brown, but elevated by a prominent and friendly Green. 

- **Navy (#1E3A5F):** Used for structural elements and primary navigation to maintain a sense of stability.
- **Green (#6BA368):** This is the "hero" color. It must be used for all primary positive actions (e.g., "Confirm," "Save," "Check-in"), success states, and "Available" badges. It signals growth and readiness.
- **Orange (#E28743):** Reserved for energetic accents and important warnings that need attention without being alarming.
- **Brown (#A47551):** Provides a warm, organic secondary tone for non-interactive elements or natural accents.
- **Dark Gray (#333333):** The standard for all text and headings to ensure high contrast and accessibility.
- **White (#FFFFFF):** The primary surface color, used aggressively to create "air" and a clean aesthetic.

## Typography

We use **Plus Jakarta Sans** exclusively to leverage its soft, rounded terminals and modern geometric construction. To achieve a friendly yet authoritative hierarchy, we use exaggerated font weights.

Headlines should use **Bold (700)** or **ExtraBold (800)** to feel confident and impactful. Body text remains at **Regular (400)** for maximum readability, while labels and small buttons use **SemiBold (600)** or **Bold (700)** to stand out against UI elements. Increased line-heights are applied across the board to ensure the type feels unhurried and accessible.

## Layout & Spacing

This design system employs a **Fluid Grid** model with an emphasis on "negative space as a feature." 

- **Desktop:** 12-column grid with 40px margins and 24px gutters.
- **Tablet:** 8-column grid with 32px margins and 20px gutters.
- **Mobile:** 4-column grid with 20px margins and 16px gutters.

Spacing follows an 8px linear scale. To maintain the "friendly" feel, vertical padding in containers and between sections should be generous—err on the side of more space to prevent the UI from feeling "dense" or "cluttered." All cards and interactive modules should use a minimum internal padding of 24px (3 units).

## Elevation & Depth

To achieve a "friendly" depth, we avoid harsh, black shadows. Instead, we use **Ambient Shadows**:
- **Soft Depth:** Low-level elevation uses a highly diffused, 15% opacity shadow tinted with the Navy primary color (e.g., `rgba(30, 58, 95, 0.15)`).
- **Floating Elements:** Modals and dropdowns use a multi-layered shadow with a 30px blur to create a soft, "pillowy" lift from the white surface.
- **Tonal Layering:** We use light gray or very pale navy backgrounds (`#F8F9FA`) to define container boundaries without needing heavy borders, maintaining a light and airy aesthetic.

## Shapes

The shape language is defined by **Rounded (0.5rem / 8px)** base corners, scaling up to **1rem (16px)** for larger components like cards, modals, and primary containers. This high degree of roundedness removes "sharpness" from the interface, reinforcing the friendly brand persona. Interactive components like buttons should feel "bouncy" and approachable, while badges and chips should use full pill-shaped (100px) rounding.

## Components

- **Buttons:** Primary buttons must use the Green (#6BA368) background with White text. They feature 16px (rounded-lg) corners and a subtle bottom-heavy shadow to look "pressable."
- **Badges:** "Available" states must use a Green (#6BA368) background with white text or a light green tint with dark green text. Always use pill-shaped rounding for badges.
- **Cards:** Cards should use a White surface, 16px corner radius, and the Soft Depth ambient shadow. Padding should be a minimum of 24px.
- **Input Fields:** Use a light gray border (#E0E0E0) that thickens and changes to Navy (#1E3A5F) on focus. Corners should be 8px.
- **Lists:** List items should be separated by whitespace rather than lines where possible. If lines are used, they must be subtle, light-gray strokes.
- **Illustrations:** Use "Friendly" spot illustrations featuring rounded characters or organic shapes in the primary Navy and Success Green to guide users through empty states or onboarding.